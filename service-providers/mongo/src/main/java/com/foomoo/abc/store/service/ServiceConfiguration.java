package com.foomoo.abc.store.service;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Configuration of service parameters.
 */
public class ServiceConfiguration {

    public static final int RECENT_FILE_COUNT;
    public static final int RECENT_REQUEST_COUNT;

    private static final String PROPERTIES_FILE_PATH = "/opt/abcstore/conf/service.properties";

    /**
     * Initialise this class based on the properties file found at path PROPERTIES_FILE_PATH.
     * If the file cannot be found or it is missing any configuration items then default values will be used.
     */
    static {
        try {
            final Configurations configurations = new Configurations();

            final Path configFilePath = Paths.get(PROPERTIES_FILE_PATH);
            final PropertiesConfiguration configuration;
            if (Files.isReadable(configFilePath)) {
                configuration = configurations.properties(configFilePath.toFile());
            } else {
                configuration = new PropertiesConfiguration();
            }

            RECENT_FILE_COUNT = configuration.getInt("recent.file.count", 10);
            RECENT_REQUEST_COUNT = configuration.getInt("recent.request.count", 10);
        } catch (ConfigurationException configurationException) {
            throw new ExceptionInInitializerError(configurationException);
        }
    }

}
