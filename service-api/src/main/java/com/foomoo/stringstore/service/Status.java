package com.foomoo.stringstore.service;

/**
 * Object representing the state of the storage service.
 */
public class Status {

    private final long strings;
    private final long requests;

    /**
     * Construct a Status object representing the state of the storage service with the given number of strings and
     * requests.
     *
     * @param strings  The number of strings stored by the service.
     * @param requests The number of requests to store strings received by the service.
     */
    public Status(final long strings, final long requests) {
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
