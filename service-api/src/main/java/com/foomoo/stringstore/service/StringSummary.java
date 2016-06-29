package com.foomoo.stringstore.service;

import java.util.UUID;

/**
 * Summary of a stored string.
 */
public class StringSummary {
    private final UUID id;
    private final long creationEpoch;
    private final int size;

    /**
     * Construct a StringSummary representing a string in storage.
     *
     * @param id            The string id.
     * @param creationEpoch The time that the string was created in storage specified as unix epoch time (the number of
     *                      milliseconds since the unix epoch).
     * @param size          The size of the string in storage.
     */
    public StringSummary(final UUID id, final long creationEpoch, final int size) {
        this.id = id;
        this.creationEpoch = creationEpoch;
        this.size = size;
    }

    /**
     * Get the string id.
     *
     * @return The string id.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Get the creation time, specified as unix epoch time (the number of milliseconds since the unix epoch).
     *
     * @return The unix epoch time that the string was created.
     */
    public long getCreationEpoch() {
        return creationEpoch;
    }

    /**
     * Get the string size.
     *
     * @return The string size.
     */
    public int getSize() {
        return size;
    }

}
