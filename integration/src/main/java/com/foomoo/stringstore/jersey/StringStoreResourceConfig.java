package com.foomoo.stringstore.jersey;

import com.foomoo.stringstore.mapper.StringNotFoundExceptionMapper;
import com.foomoo.stringstore.mapper.MongoTimeoutExceptionMapper;
import com.foomoo.stringstore.resource.StringsResource;
import com.foomoo.stringstore.resource.RequestsResource;
import com.foomoo.stringstore.resource.StatusResource;
import com.foomoo.stringstore.service.StringsService;
import com.foomoo.stringstore.service.MongoStringsService;
import com.foomoo.stringstore.service.MongoStatusService;
import com.foomoo.stringstore.service.StatusService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;
import java.util.HashSet;
import java.util.Set;

/**
 * The String Store Jersey ResourceConfig using MongoDB.
 */
@ApplicationPath("/")
public class StringStoreResourceConfig extends ResourceConfig {

    public StringStoreResourceConfig() {

        final Set<Class<?>> classes = new HashSet<>();
        classes.add(MultiPartFeature.class);
        classes.add(JacksonFeature.class);
        classes.add(StringNotFoundExceptionMapper.class);
        classes.add(MongoTimeoutExceptionMapper.class);
        classes.add(StatusResource.class);
        classes.add(StringsResource.class);
        classes.add(RequestsResource.class);

        registerClasses(classes);

        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(MongoStatusService.class).to(StatusService.class);
                bind(MongoStringsService.class).to(StringsService.class);
            }
        });
    }

}
