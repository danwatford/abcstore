package com.foomoo.abc.store.service;

import java.util.UUID;

/**
 * Summary of a request to add a file to storage.
 */
public class RequestSummary {
    private final UUID id;
    private final UUID fileId;
    private final long epoch;
    private final String user;

    /**
     * Create a RequestSummary representing a request to add a file to storage.
     *
     * @param id     The id of the request.
     * @param fileId The id of the file that the request relates to.
     * @param epoch  The time that the request was created specified as unix epoch time (the number of milliseconds
     *               since the unix epoch).
     * @param user   The user who made the request.
     */
    public RequestSummary(final UUID id, final UUID fileId, final long epoch, final String user) {
        this.id = id;
        this.fileId = fileId;
        this.epoch = epoch;
        this.user = user;
    }

    /**
     * Get the id of the request.
     *
     * @return The request id.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Get the id of the file related to the request.
     *
     * @return The file id.
     */
    public UUID getFileId() {
        return fileId;
    }

    /**
     * The time that the request was made, specified as unix epoch time (the number of milliseconds since the unix
     * epoch).
     *
     * @return The unix epoch time that the request was made.
     */
    public long getEpoch() {
        return epoch;
    }

    /**
     * The user who made the request.
     *
     * @return The user.
     */
    public String getUser() {
        return user;
    }

}
