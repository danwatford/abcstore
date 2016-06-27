package com.foomoo.stringstore.resource;

import com.foomoo.stringstore.message.RequestSummaryMessage;
import com.foomoo.stringstore.service.FilesService;
import com.foomoo.stringstore.service.RequestSummary;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Root resource for requests.
 */
@Path("requests")
public class RequestsResource {

    @Inject
    private FilesService filesService;

    /**
     * Gets summaries of the most recent requests to add files.
     *
     * @return A success response containing a {@link java.util.List} of {@link RequestSummaryMessage} objects.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRequests() {

        final List<RequestSummary> requests = filesService.getRequests();

        final List<RequestSummaryMessage> requestSummaries = requests.stream().map(RequestsResource::toRequestSummaryMessage).collect(Collectors.toList());

        return Response.ok(requestSummaries).build();
    }

    /**
     * Transform a RequestSummary into a RequestSummaryMessage.
     *
     * @param requestSummary The RequestSummary to transform.
     * @return The created RequestSummaryMessage.
     */
    private static RequestSummaryMessage toRequestSummaryMessage(final RequestSummary requestSummary) {

        return new RequestSummaryMessage(requestSummary.getId(), requestSummary.getFileId(), requestSummary.getEpoch(), requestSummary.getUser());
    }
}
