package com.foomoo.abc.store.resource;

import com.foomoo.abc.store.service.FilesService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Root resource for requests.
 */
@Path("requests")
public class RequestsResource {

    /**
     * Gets summaries of the most recent requests to add files.
     *
     * @return A success response containing a {@link java.util.List} of {@link com.foomoo.abc.store.message.RequestSummary}
     * objects.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRequests() {

        final FilesService filesService = new FilesService();

        return Response.ok(filesService.getRequests()).build();
    }

}
