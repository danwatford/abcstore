package com.foomoo.stringstore.mapper;

import com.foomoo.stringstore.service.StringNotFoundException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Implementation of {@link ExceptionMapper} to give a Not Found response in the event of a requested file not being
 * found.
 */
@Provider
public class FileNotFoundExceptionMapper implements ExceptionMapper<StringNotFoundException> {

    /**
     * Convert the given FileNoteFoundException to a not found response containing the message from the exception.
     *
     * @return A not found response.
     */
    @Override
    public Response toResponse(final StringNotFoundException exception) {

        return Response.status(Response.Status.NOT_FOUND)
                .entity(exception.getMessage())
                .type(MediaType.TEXT_PLAIN_TYPE)
                .build();
    }

}
