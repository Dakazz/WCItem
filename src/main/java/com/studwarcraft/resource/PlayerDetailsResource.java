package com.studwarcraft.resource;

import com.studwarcraft.exception.PlayerException;
import com.studwarcraft.model.PlayerDetails;
import com.studwarcraft.service.PlayerDetailsService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/playerDetails")
public class PlayerDetailsResource {

    @Inject
    private PlayerDetailsService detailsService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/addDetails")
    public Response addPlayerDetails(PlayerDetails details) {
        try {
            detailsService.createPlayerDetails(details);
        } catch (PlayerException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAllDetails")
    public Response getAllPlayerDetails() {
        List<PlayerDetails> details = null;
        try {
            details = detailsService.getAllPlayerDetails();
        } catch (PlayerException e) {
            return Response.status(Response.Status.NO_CONTENT).entity(e.getMessage()).build();
        }
        return Response.ok().entity(details).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getDetailsByPlayerId")
    public Response getDetailsByPlayerId(@QueryParam("playerId") Long playerId) {
        PlayerDetails details = detailsService.getDetailsByPlayerId(playerId);
        if(details == null) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok().entity(details).build();
    }
}