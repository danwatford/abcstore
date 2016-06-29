package com.foomoo.stringstore.service;

import java.util.UUID;

/**
 * Thrown to indicate that a string could not be found in the string store.
 */
public class StringNotFoundException extends Throwable {

    /**
     * Constructs a new StringNotFoundException indicating that the string with the given string id could not be found
     * in the string store.
     *
     * @param id The string id that could not be resolved to a string.
     */
    public StringNotFoundException(final UUID id) {
        super(String.format("Cannot find string with id: %s", id));
    }

}
