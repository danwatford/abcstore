package com.foomoo.stringstore.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request to add a file.
 */
public class AddFileRequestMessage {

    private final String user;

    /**
     * Construct an AddFileRequestMessage representing the user making the request to add a file.
     *
     * @param user The user making the add request.
     */
    @JsonCreator
    public AddFileRequestMessage(@JsonProperty("user") final String user) {
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
