package com.foomoo.stringstore.app;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

/**
 * File Store service running in a Grizzly HTTP Server.
 */
public class App {

    public static void main(final String[] args) throws IOException {

        final URI baseUri = UriBuilder.fromUri("http://localhost/").port(4982).build();
        final ResourceConfig resourceConfig = new FileStoreResourceConfig();
        final HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(baseUri, resourceConfig);

        httpServer.start();
    }

}
