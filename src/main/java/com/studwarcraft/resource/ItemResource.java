package com.studwarcraft.resource;

import com.studwarcraft.exception.ItemException;
import com.studwarcraft.model.FileUploadForm;
import com.studwarcraft.model.Item;
import com.studwarcraft.model.UploadedFile;
import com.studwarcraft.service.ItemService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import java.util.List;
import jakarta.annotation.security.PermitAll;

@Path("/item")
@PermitAll

public class ItemResource {

    @Inject
    private ItemService itemService;

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addItem(Item item) {
        try {
            itemService.createItem(item);
        } catch (ItemException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllItems() {
        try {
            List<Item> items = itemService.getAllItems();
            return Response.ok(items).build();
        } catch (ItemException e) {
            return Response.status(Response.Status.NO_CONTENT).entity(e.getMessage()).build();
        }
    }

    // Zadatak 2: POST multipart upload
    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(
            @QueryParam("itemId") Long itemId,
            @MultipartForm FileUploadForm form) {

        if (itemId == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Query parametar itemId je obavezan.").build();
        }

        try {
            UploadedFile existing = itemService.uploadFileToItem(
                    itemId, form.getFileName(), form.getFile());

            if (existing != null) {
                // Fajl sa tim imenom vec postoji
                return Response.status(Response.Status.CONFLICT)
                        .entity("Fajl '" + form.getFileName() +
                                "' vec postoji na putanji: " + existing.getFilename())
                        .build();
            }

            return Response.ok("Fajl uspjesno uploadovan.").build();

        } catch (ItemException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Greska pri uploadu: " + e.getMessage()).build();
        }
    }

    // Zadatak 3: GET item sa fajlovima ucitanim s diska
    @GET
    @Path("/getById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItemById(@PathParam("id") Long id) {
        try {
            Item item = itemService.getItemWithFiles(id);
            return Response.ok(item).build();
        } catch (ItemException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        }
    }
}