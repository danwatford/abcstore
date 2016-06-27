package com.foomoo.stringstore.service;

import java.util.UUID;

/**
 * Summary of a stored file.
 */
public class FileSummary {
    private final UUID id;
    private final long creationEpoch;
    private final int size;

    /**
     * Construct a FileSummary representing a file in storage.
     *
     * @param id            The file id.
     * @param creationEpoch The time that the file was created in storage specified as unix epoch time (the number of
     *                      milliseconds since the unix epoch).
     * @param size          The size of the file in storage.
     */
    public FileSummary(final UUID id, final long creationEpoch, final int size) {
        this.id = id;
        this.creationEpoch = creationEpoch;
        this.size = size;
    }

    /**
     * Get the file id.
     *
     * @return The file id.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Get the creation time, specified as unix epoch time (the number of milliseconds since the unix epoch).
     *
     * @return The unix epoch time that the file was created.
     */
    public long getCreationEpoch() {
        return creationEpoch;
    }

    /**
     * Get the file size.
     *
     * @return The file size.
     */
    public int getSize() {
        return size;
    }

}
