package com.foomoo.stringstore.store;

import java.util.UUID;

/**
 * Represents the data stored in the requests collection in the MongoDB database.
 */
public class DbRequest {

    private final UUID id;
    private final String user;
    private final UUID stringId;
    private final long epoch;

    /**
     * Construct a DbRequest representing a document in the requests collection in the MongoDB database.
     *
     * @param id       The request id.
     * @param user     The user who made the request.
     * @param stringId The id of the string resulting from the request.
     * @param epoch    The unix time that the request was made.
     */
    public DbRequest(final UUID id, final String user, final UUID stringId, final long epoch) {
        this.id = id;
        this.user = user;
        this.stringId = stringId;
        this.epoch = epoch;
    }

    /**
     * Get the request id.
     *
     * @return The request id.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Get the user who made the request.
     *
     * @return The user.
     */
    public String getUser() {
        return user;
    }

    /**
     * Get the string id resulting from the request. This is either the id of a new string added to the store as a
     * result of the request, or an existing string which matched the content of the request.
     *
     * @return The string id.
     */
    public UUID getStringId() {
        return stringId;
    }

    /**
     * Get the unix time that the request was made.
     *
     * @return The request creation time.
     */
    public long getEpoch() {
        return epoch;
    }

}
