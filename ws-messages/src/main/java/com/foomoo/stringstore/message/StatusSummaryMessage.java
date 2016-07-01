package com.foomoo.stringstore.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Object representing the state of the storage service.
 */
public class StatusSummaryMessage {

    private final long strings;
    private final long requests;

    /**
     * Construct a StatusSummaryMessage object representing the state of the storage service with the given number of
     * strings and requests.
     *
     * @param strings  The number of strings stored by the service.
     * @param requests The number of requests to store strings received by the service.
     */
    @JsonCreator
    public StatusSummaryMessage(@JsonProperty("strings") final long strings, @JsonProperty("requests") final long requests) {
        this.strings = strings;
        this.requests = requests;
    }

    /**
     * Get the number of strings stored by the storage service.
     *
     * @return The number of strings.
     */
    public long getStrings() {
        return strings;
    }

    /**
     * Get the number of requests to store strings received by the storage service.
     *
     * @return The number of requests.
     */
    public long getRequests() {
        return requests;
    }

}
