package com.foomoo.stringstore.service;

import java.util.UUID;

/**
 * Result of adding a string to the string store.
 */
public class AddStringResult {

    private final UUID stringId;
    private final UUID requestId;

    private final String user;
    private final boolean existingString;

    /**
     * Construct an AddStringResult representing the result of adding a string to the string store.
     *
     * @param stringId       The id of the added string.
     * @param requestId    The id of the request to add the string.
     * @param user         The user making the add string request.
     * @param existingString Indicate whether the string already existed in the string store.
     */
    public AddStringResult(final UUID stringId, final UUID requestId, final String user, final boolean existingString) {
        this.stringId = stringId;
        this.requestId = requestId;
        this.user = user;
        this.existingString = existingString;
    }

    /**
     * Get the user who made the request to add the string.
     *
     * @return The user.
     */
    public String getUser() {
        return user;
    }

    /**
     * Get the id of the string resulting from the add string request.
     *
     * @return The string id.
     */
    public UUID getStringId() {
        return stringId;
    }

    /**
     * Get the id of the add string request.
     *
     * @return The request id.
     */
    public UUID getRequestId() {
        return requestId;
    }

    /**
     * Did the string already exist in the string store.
     *
     * @return True if the string already existed, false otherwise.
     */
    public boolean isExistingString() {
        return existingString;
    }

}
