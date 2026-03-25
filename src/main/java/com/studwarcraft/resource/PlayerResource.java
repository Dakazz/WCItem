package com.studwarcraft.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.studwarcraft.exception.PlayerException;
import com.studwarcraft.model.Player;
import com.studwarcraft.service.PlayerService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/player")
public class PlayerResource {

    @Inject
    private PlayerService playerService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/add")
    public Response addPlayer(Player player) {
        try {
            playerService.createPlayer(player);
        } catch (PlayerException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public Response getAllPlayers() {
        try {
            List<Player> players = playerService.getAllPlayers();

            List<Map<String, String>> simplePlayers = players.stream()
                    .map(p -> Map.of(
                            "name", p.getName(),
                            "lastname", p.getLastname()
                    ))
                    .collect(Collectors.toList());

            return Response.ok(simplePlayers).build();
        } catch (PlayerException e) {
            return Response.status(Response.Status.NO_CONTENT).entity(e.getMessage()).build();
        }
    }
}