package com.foomoo.abc.store.resource;

import com.foomoo.abc.store.message.StatusSummaryMessage;
import com.foomoo.abc.store.service.Status;
import com.foomoo.abc.store.service.StatusService;

import javax.inject.Inject;
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

    @Inject
    private StatusService statusService;

    /**
     * Get the status of the service.
     *
     * @return A success response containing a {@link StatusSummaryMessage} entity.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStatus() {

        final Status status = statusService.getStatus();

        final StatusSummaryMessage statusSummaryMessage = new StatusSummaryMessage(status.getFiles(), status.getRequests());

        return Response.ok(statusSummaryMessage).build();
    }

}
