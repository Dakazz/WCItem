package com.studwarcraft.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.studwarcraft.exception.ProfileException;
import com.studwarcraft.model.Profile;
import com.studwarcraft.service.ProfileService;

import java.util.List;

@Path("/profile")
public class ProfileResource {

    @Inject
    private ProfileService profileService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/add")
    public Response addProfile(Profile profile) {
        try {
            profileService.createProfile(profile);
        } catch (ProfileException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public Response getAllProfiles() {
        try {
            List<Profile> profiles = profileService.getAllProfiles();
            return Response.ok(profiles).build();
        } catch (ProfileException e) {
            return Response.status(Response.Status.NO_CONTENT).entity(e.getMessage()).build();
        }
    }
}