package com.foomoo.abc.store.app;

import com.foomoo.abc.store.resource.FilesResource;
import com.foomoo.abc.store.resource.RequestsResource;
import com.foomoo.abc.store.resource.StatusResource;
import com.foomoo.abc.store.service.FileNotFoundExceptionMapper;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;
import java.util.HashSet;
import java.util.Set;

/**
 * The File Store Jersey ResourceConfig
 */
@ApplicationPath("/")
public class FileStoreResourceConfig extends ResourceConfig {

    public FileStoreResourceConfig() {

        final Set<Class<?>> classes = new HashSet<>();
        classes.add(MultiPartFeature.class);
        classes.add(JacksonFeature.class);
        classes.add(FileNotFoundExceptionMapper.class);
        classes.add(MongoTimeoutExceptionMapper.class);
        classes.add(StatusResource.class);
        classes.add(FilesResource.class);
        classes.add(RequestsResource.class);

        registerClasses(classes);
    }

}
