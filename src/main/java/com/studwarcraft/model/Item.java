package com.studwarcraft.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(
                name = Item.GET_ALL_ITEMS,
                query = "Select distinct i from Item i"
        ),
        @NamedQuery(
                name = Item.GET_ITEM_BY_ID,
                query = "Select distinct i from Item i " +
                        "left join fetch i.uploadedFiles " +
                        "where i.itemid = :idI"
        )
})
public class Item {

    public static final String GET_ALL_ITEMS = "GetAllItems";
    public static final String GET_ITEM_BY_ID = "GetItemById";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_seq")
    @SequenceGenerator(name = "item_seq", sequenceName = "item_seq", allocationSize = 1)
    private Long itemid;

    private String name;
    private String category;

    @Column(name = "function")
    private String function;

    private String rarity;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "item_uploaded_files",
            joinColumns = @JoinColumn(name = "itemid"),
            inverseJoinColumns = @JoinColumn(name = "fileid")
    )
    private List<UploadedFile> uploadedFiles = new ArrayList<>();

    public Item() {}

    public Long getItemid() { return itemid; }
    public void setItemid(Long itemid) { this.itemid = itemid; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getFunction() { return function; }
    public void setFunction(String function) { this.function = function; }
    public String getRarity() { return rarity; }
    public void setRarity(String rarity) { this.rarity = rarity; }
    public List<UploadedFile> getUploadedFiles() { return uploadedFiles; }
    public void setUploadedFiles(List<UploadedFile> uploadedFiles) { this.uploadedFiles = uploadedFiles; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Item i)) return false;
        return Objects.equals(itemid, i.itemid) && Objects.equals(name, i.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemid, name);
    }
}