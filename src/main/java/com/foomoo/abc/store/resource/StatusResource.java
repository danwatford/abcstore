package com.foomoo.abc.store.resource;

import com.foomoo.abc.store.message.Status;
import com.foomoo.abc.store.service.StatusService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Root resource for status requests.
 */
@Path("status")
public class StatusResource {

    /**
     * Get the status of the service.
     *
     * @return A success response containing a {@link Status} entity.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStatus() {

        final StatusService statusService = new StatusService();
        final Status status = statusService.getStatus();

        return Response.ok(status).build();
    }

}
