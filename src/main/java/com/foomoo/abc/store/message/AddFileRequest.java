package com.foomoo.abc.store.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request to add a file.
 */
public class AddFileRequest {

    private final String user;

    /**
     * Construct an AddFileRequest representing the user making the request to add a file.
     *
     * @param user The user making the add request.
     */
    @JsonCreator
    public AddFileRequest(@JsonProperty("user") final String user) {
        this.user = user;
    }

    /**
     * Get the user who initiated the add request.
     *
     * @return The user.
     */
    public String getUser() {
        return user;
    }

}
