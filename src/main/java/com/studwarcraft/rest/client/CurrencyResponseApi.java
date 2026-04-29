package com.studwarcraft.rest.client;

import com.studwarcraft.model.CurrencyResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/api/rates")
@RegisterRestClient(configKey = "currencyresponse-api")
public interface CurrencyResponseApi {

    @GET
    CurrencyResponse getRate(
            @QueryParam("from") String from,
            @QueryParam("to") String to
    );
}