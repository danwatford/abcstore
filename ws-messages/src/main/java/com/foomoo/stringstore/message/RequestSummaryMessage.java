package com.foomoo.stringstore.message;

import java.util.UUID;

/**
 * Summary of a request to add a string to storage.
 */
public class RequestSummaryMessage {
    private final UUID id;
    private final UUID stringId;
    private final long epoch;
    private final String user;

    /**
     * Create a RequestSummaryMessage representing a request to add a string to storage.
     *
     * @param id       The id of the request.
     * @param stringId The id of the string that the request relates to.
     * @param epoch    The time that the request was created specified as unix epoch time (the number of milliseconds
     *                 since the unix epoch).
     * @param user     The user who made the request.
     */
    public RequestSummaryMessage(final UUID id, final UUID stringId, final long epoch, final String user) {
        this.id = id;
        this.stringId = stringId;
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
     * Get the id of the string related to the request.
     *
     * @return The string id.
     */
    public UUID getStringId() {
        return stringId;
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
