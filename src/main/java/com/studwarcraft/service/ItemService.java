package com.studwarcraft.service;

import com.studwarcraft.exception.ItemException;
import com.studwarcraft.model.Item;
import com.studwarcraft.model.UploadedFile;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Dependent
public class ItemService {

    private static final String UPLOAD_DIR = "C:/Users/Daka/Documents/EclipseWorkaraund/studwarcraft/uploads/";

    @Inject
    private EntityManager em;

    @Transactional
    public Item createItem(Item item) throws ItemException {
        if (item == null) throw new ItemException("Item nije proslijeđen.");
        if (item.getName() == null || item.getName().isEmpty()) throw new ItemException("Naziv itema je prazan.");
        return em.merge(item);
    }

    public List<Item> getAllItems() throws ItemException {
        List<Item> items = em.createNamedQuery(Item.GET_ALL_ITEMS, Item.class).getResultList();
        if (items.isEmpty()) throw new ItemException("Nema itema.");
        return items;
    }

    public Item getItemById(Long id) throws ItemException {
        try {
            return em.createNamedQuery(Item.GET_ITEM_BY_ID, Item.class)
                    .setParameter("idI", id)
                    .getSingleResult();
        } catch (Exception e) {
            throw new ItemException("Item sa id " + id + " ne postoji.");
        }
    }

    // Vraca postojeci UploadedFile ako fajl vec postoji, null ako je novi
    @Transactional
    public UploadedFile uploadFileToItem(Long itemId, String fileName, InputStream fileStream)
            throws ItemException, IOException {

        // 1. Ucitaj item po proslijedjenom id-ju
        Item item = em.find(Item.class, itemId);
        if (item == null) throw new ItemException("Item sa id " + itemId + " ne postoji.");

        String filePath = UPLOAD_DIR + fileName;

        // 2. Provjeri da li fajl sa tim imenom vec postoji u bazi
        UploadedFile existing = findByFilename(filePath);
        if (existing != null) {
            // Fajl vec postoji - povezi ga sa itemom ukoliko vec nije povezan
            if (!item.getUploadedFiles().contains(existing)) {
                item.getUploadedFiles().add(existing);
                em.merge(item);
            }
            return existing; // signal: fajl vec postoji
        }

        // 3. Sacuvaj fajl na fajlsistem
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        Files.copy(fileStream, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        // 4. Kreiraj UploadedFile i sacuvaj putanju u filename
        UploadedFile uploadedFile = new UploadedFile();
        uploadedFile.setFilename(filePath);

        // 5. Povezi item i uploadedFile, odraditi update itema
        item.getUploadedFiles().add(uploadedFile);
        em.persist(uploadedFile);
        em.merge(item);

        return null; // signal: novi fajl, sve OK
    }

    // Ucitaj item sa fajlovima ucitanim s diska (za GET endpoint)
    public Item getItemWithFiles(Long id) throws ItemException {
        Item item = getItemById(id);
        for (UploadedFile uf : item.getUploadedFiles()) {
            File f = new File(uf.getFilename());
            if (f.exists()) {
                uf.setFile(f);
            }
        }
        return item;
    }

    private UploadedFile findByFilename(String filePath) {
        List<UploadedFile> results = em.createQuery(
                "Select u from UploadedFile u where u.filename = :fn", UploadedFile.class)
                .setParameter("fn", filePath)
                .getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}