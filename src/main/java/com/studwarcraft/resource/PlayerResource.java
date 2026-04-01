package com.studwarcraft.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.studwarcraft.model.Player;
import com.studwarcraft.service.PlayerService;

import java.util.List;

@Path("/player")
public class PlayerResource {

    @Inject
    private PlayerService playerService;

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPlayer(Player player) {
        playerService.createPlayer(player);
        return Response.ok().build();
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlayers() {
        List<Player> players = playerService.getAllPlayers();
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