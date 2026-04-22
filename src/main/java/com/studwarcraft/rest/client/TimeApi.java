package com.studwarcraft.rest.client;

import com.studwarcraft.model.TimeResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/api/time/current")
@RegisterRestClient(configKey = "time-api")
public interface TimeApi {

    @GET
    @Path("/ip")
    TimeResponse getTimeByIp(@QueryParam("ipAddress") String ip);
}
