package com.starea.boundary;

import com.starea.control.EnvironmentService;
import com.starea.model.EnvironmentInfo;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/environment")
public class EnvironmentResource {

    @Inject
    EnvironmentService environmentService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response createEnvironment(EnvironmentInfo environmentInfo) {
        environmentService.createEnvironment(environmentInfo);
        return Response.ok().build();
    }
}
