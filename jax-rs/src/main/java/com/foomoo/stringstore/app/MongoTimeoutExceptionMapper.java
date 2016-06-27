package com.foomoo.stringstore.app;

import com.mongodb.MongoTimeoutException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Failure to interact with the MongoDB server is presented to us as a {@link MongoTimeoutException}. Map this to a
 * Gateway Timeout response.
 */
@Provider
public class MongoTimeoutExceptionMapper implements ExceptionMapper<MongoTimeoutException> {

    /**
     * Convert the given MongoTimeoutException to a gateway timeout response.
     *
     * @return A gateway timeout response.
     */
    @Override
    public Response toResponse(final MongoTimeoutException exception) {

        return Response.status(Response.Status.GATEWAY_TIMEOUT)
                .build();
    }

}
