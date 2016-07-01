package com.foomoo.stringstore.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request to add a string.
 */
public class AddStringRequestMessage {

    private final String user;

    /**
     * Construct an AddStringRequestMessage representing the user making the request to add a string.
     *
     * @param user The user making the add request.
     */
    @JsonCreator
    public AddStringRequestMessage(@JsonProperty("user") final String user) {
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
