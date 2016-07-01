package com.foomoo.stringstore.resource;

import com.foomoo.stringstore.message.AddStringRequestMessage;
import com.foomoo.stringstore.message.StringSummaryMessage;
import com.foomoo.stringstore.service.AddStringResult;
import com.foomoo.stringstore.service.StringNotFoundException;
import com.foomoo.stringstore.service.StringsService;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

/**
 * Root resource for string requests.
 */
@Path("strings")
public class StringsResource {

    @Inject
    private StringsService stringsService;

    /**
     * Adds a string to the store.
     *
     * @param addStringRequestMessage Metadata for string to be stored.
     * @param content                 The string content to store.
     * @return A success response containing an {@link AddStringResult} entity.
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response addString(@FormDataParam("request") AddStringRequestMessage addStringRequestMessage,
                              @FormDataParam("content") String content) {

        final AddStringResult addStringResult = stringsService.addString(addStringRequestMessage.getUser(), content);

        return Response.ok(addStringResult).build();
    }

    /**
     * Gets summaries of the most recently added strings.
     *
     * @return A success response containing a {@link java.util.List} of {@link StringSummaryMessage} objects.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStrings() {

        return Response.ok(stringsService.getStringSummaries()).build();
    }

    /**
     * Get the summary for the string specified by the given string id.
     *
     * @param stringId The string id.
     * @return A success response containing a {@link StringSummaryMessage} entity.
     * @throws StringNotFoundException If the string for the given string id cannot be found.
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStringSummary(@PathParam("id") final UUID stringId) throws StringNotFoundException {

        return Response.ok(stringsService.getStringSummary(stringId)).build();
    }

    /**
     * Gets the content of the string specified by the given string id.
     *
     * @param stringId The string id.
     * @return A success response containing the string content as a {@link String} entity.
     * @throws StringNotFoundException If the string for the given string id cannot be found.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{id}/content")
    public Response getStringContent(@PathParam("id") final UUID stringId) throws StringNotFoundException {

        return Response.ok(stringsService.getStringContent(stringId)).build();
    }

}
