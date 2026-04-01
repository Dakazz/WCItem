package com.studwarcraft.resource;

import com.studwarcraft.exception.PlayerException;
import com.studwarcraft.model.Profile;
import com.studwarcraft.service.ProfileService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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
        } catch (PlayerException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public Response getAllProfiles() {
        List<Profile> profiles = null;
        try {
            profiles = profileService.getAllProfiles();
        } catch (PlayerException e) {
            return Response.status(Response.Status.NO_CONTENT).entity(e.getMessage()).build();
        }
        return Response.ok().entity(profiles).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getProfileByPlayerId")
    public Response getProfileByPlayerId(@QueryParam("playerId") Long playerId) {
        Profile profile = profileService.getProfileByPlayerId(playerId);
        if(profile == null) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok().entity(profile).build();
    }
}