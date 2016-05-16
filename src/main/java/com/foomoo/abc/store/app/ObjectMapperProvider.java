package com.foomoo.abc.store.app;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * Provider for the Jackson ObjectMapper to be used for reading/writing JSON.
 */
@Provider
public class ObjectMapperProvider implements ContextResolver<ObjectMapper> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }

}
