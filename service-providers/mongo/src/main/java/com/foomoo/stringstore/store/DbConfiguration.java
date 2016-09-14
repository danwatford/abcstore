package com.foomoo.stringstore.store;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Configuration of database connection and operations.
 */
public class DbConfiguration {

    public static final String HOSTNAME;
    public static final int PORT;

    public static final String DATABASE;
    public static final String STRINGS_COLLECTION;
    public static final String REQUESTS_COLLECTION;

    private static final String PROPERTIES_FILE_PATH_SYSTEM_PROPERTY = "mongo.store.db.properties";
    private static final String DEFAULT_PROPERTIES_FILE_PATH = "/opt/stringstore/conf/db.properties";

    /**
     * Initialise this class based on the properties file found at path DEFAULT_PROPERTIES_FILE_PATH.
     * If the file cannot be found or it is missing any configuration items then default values will be used.
     */
    static {
        try {
            final Configurations configurations = new Configurations();

            final Path configFilePath = Paths.get(System.getProperty(PROPERTIES_FILE_PATH_SYSTEM_PROPERTY, DEFAULT_PROPERTIES_FILE_PATH));
            final PropertiesConfiguration configuration;
            if (Files.isReadable(configFilePath)) {
                configuration = configurations.properties(configFilePath.toFile());
            } else {
                configuration = new PropertiesConfiguration();
            }

            HOSTNAME = configuration.getString("hostname", "localhost");
            PORT = configuration.getInt("port", 27017);

            DATABASE = configuration.getString("database", "stringstore");
            STRINGS_COLLECTION = configuration.getString("collection.strings", "strings");
            REQUESTS_COLLECTION = configuration.getString("collection.requests", "requests");
        } catch (ConfigurationException configurationException) {
            throw new ExceptionInInitializerError(configurationException);
        }
    }

}
