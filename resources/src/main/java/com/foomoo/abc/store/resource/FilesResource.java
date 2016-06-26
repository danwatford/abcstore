package com.foomoo.abc.store.resource;

import com.foomoo.abc.store.message.AddFileRequestMessage;
import com.foomoo.abc.store.message.FileSummaryMessage;
import com.foomoo.abc.store.service.AddFileResult;
import com.foomoo.abc.store.service.FileNotFoundException;
import com.foomoo.abc.store.service.FilesService;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

/**
 * Root resource for file requests.
 */
@Path("files")
public class FilesResource {

    @Inject
    private FilesService filesService;

    /**
     * Adds a file to the store.
     *
     * @param addFileRequestMessage Metadata for file to be stored.
     * @param content        The file content to store.
     * @return A success response containing an {@link AddFileResult} entity.
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response addFile(@FormDataParam("request") AddFileRequestMessage addFileRequestMessage,
                            @FormDataParam("content") String content) {

        final AddFileResult addFileResult = filesService.addFile(addFileRequestMessage.getUser(), content);

        return Response.ok(addFileResult).build();
    }

    /**
     * Gets summaries of the most recently added files.
     *
     * @return A success response containing a {@link java.util.List} of {@link FileSummaryMessage} objects.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFiles() {

        return Response.ok(filesService.getFiles()).build();
    }

    /**
     * Get the summary for the file specified by the given file id.
     *
     * @param fileId The file id.
     * @return A success response containing a {@link FileSummaryMessage} entity.
     * @throws FileNotFoundException If the file for the given file id cannot be found.
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFileSummary(@PathParam("id") final UUID fileId) throws FileNotFoundException {

        return Response.ok(filesService.getFile(fileId)).build();
    }

    /**
     * Gets the content of the file specified by the given file id.
     *
     * @param fileId The file id.
     * @return A success response containing the file content as a {@link String} entity.
     * @throws FileNotFoundException If the file for the given file id cannot be found.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{id}/content")
    public Response getFileContent(@PathParam("id") final UUID fileId) throws FileNotFoundException {

        return Response.ok(filesService.getFileContent(fileId)).build();
    }

}
