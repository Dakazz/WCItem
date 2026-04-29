package com.studwarcraft.resource;

import com.studwarcraft.model.CurrencyResponse;
import com.studwarcraft.model.Player;
import com.studwarcraft.rest.client.CurrencyResponseApi;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/currencyConversion")
public class CurrencyConversionResource {

    @Inject
    @RestClient
    CurrencyResponseApi client;

    @Inject
    EntityManager em;

    @GET
    @RolesAllowed("admin")
    @Transactional
    public Response convert(
            @QueryParam("from") String from,
            @QueryParam("to") String to,
            @QueryParam("value") double value,
            @QueryParam("userId") Long userId
    ) {
        if (from == null || from.isBlank() || to == null || to.isBlank() || userId == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Query parametri from, to, value i userId su obavezni.")
                    .build();
        }

        CurrencyResponse response = client.getRate(from, to);

        response.setValue(value);
        response.setConvertedValue(value * response.getRate());

        Player player = em.find(Player.class, userId);
        if (player == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Player sa id " + userId + " ne postoji.")
                    .build();
        }

        response.setPlayer(player);
        player.addCurrencyResponse(response);

        em.persist(response);

        return Response.ok().entity(response).build();
    }
}
