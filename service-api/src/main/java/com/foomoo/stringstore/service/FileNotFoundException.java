package com.foomoo.stringstore.service;

import java.util.UUID;

/**
 * Thrown to indicate that a file could not be found in the file store.
 */
public class FileNotFoundException extends Throwable {

    /**
     * Constructs a new FileNotFoundException indicating that the file with the given file id could not be found in the
     * file store.
     *
     * @param id The file id that could not be resolved to a file.
     */
    public FileNotFoundException(final UUID id) {
        super(String.format("Cannot find file with id: %s", id));
    }

}
