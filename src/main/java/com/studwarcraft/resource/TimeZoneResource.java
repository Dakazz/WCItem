package com.studwarcraft.resource;

import com.studwarcraft.exception.PlayerException;
import com.studwarcraft.model.Player;
import com.studwarcraft.model.PlayerTimeZone;
import com.studwarcraft.model.TimeResponse;
import com.studwarcraft.rest.client.IpApi;
import com.studwarcraft.rest.client.TimeApi;
import com.studwarcraft.service.PlayerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("")
public class TimeZoneResource {

    @Inject
    private PlayerService playerService;

    @Inject
    @RestClient
    private IpApi ipApi;

    @Inject
    @RestClient
    private TimeApi timeApi;

    @GET
    @Path("/getTimezoneByIP")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTimezoneByIP(@QueryParam("userid") Long userId) {
        if (userId == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Query parametar userid je obavezan.")
                    .build();
        }

        try {
            Player player = playerService.getPlayerById(userId);
            if (player == null) {
                throw new PlayerException("Player sa id " + userId + " ne postoji.");
            }

            String ipAddress = ipApi.getIp();
            TimeResponse timeResponse = timeApi.getTimeByIp(ipAddress.trim());

            PlayerTimeZone playerTimeZone = PlayerTimeZone.fromResponse(timeResponse);
            playerService.addTimeZoneToPlayer(player.getPlayerid(), playerTimeZone);

            return Response.ok().entity(playerTimeZone).build();
        } catch (PlayerException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }
}
