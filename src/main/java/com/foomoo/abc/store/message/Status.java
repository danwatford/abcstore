package com.foomoo.abc.store.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Object representing the state of the storage service.
 */
public class Status {

    private final long files;
    private final long requests;

    /**
     * Construct a Status object representing the state of the storage service with the given number of files and
     * requests.
     *
     * @param files    The number of files stored by the service.
     * @param requests The number of requests to store files received by the service.
     */
    @JsonCreator
    public Status(@JsonProperty("files") final long files, @JsonProperty("requests") final long requests) {
        this.files = files;
        this.requests = requests;
    }

    /**
     * Get the number of files stored by the storage service.
     *
     * @return The number of files.
     */
    public long getFiles() {
        return files;
    }

    /**
     * Get the number of requests to store files received by the storage service.
     *
     * @return The number of requests.
     */
    public long getRequests() {
        return requests;
    }

}
