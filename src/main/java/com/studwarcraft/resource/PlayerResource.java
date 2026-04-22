package com.studwarcraft.resource;

import com.studwarcraft.exception.PlayerException;
import com.studwarcraft.model.Player;
import com.studwarcraft.service.PlayerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/player")
public class PlayerResource {

    @Inject
    private PlayerService playerService;

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPlayer(Player player) {
        try {
            playerService.createPlayer(player);
        } catch (PlayerException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlayers() {
        List<Player> players = null;
        try {
            players = playerService.getAllPlayers();
        } catch (PlayerException e) {
            return Response.status(Response.Status.NO_CONTENT).entity(e.getMessage()).build();
        }
        return Response.ok(players).build();
    }

    @GET
    @Path("/getPlayerById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlayerById(@PathParam("id") Long id) {
        Player player = playerService.getPlayerById(id);
        if (player == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(player).build();
    }

    @GET
    @Path("/getPlayersByName")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlayersByName(@QueryParam("name") String name) {
        List<Player> players = playerService.getPlayersByName(name);
        return Response.ok(players).build();
    }

    @GET
    @Path("/getPlayersByRole")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlayersByRole(@QueryParam("role") String role) {
        List<Player> players = playerService.getPlayersByRole(role);
        return Response.ok(players).build();
    }
}
