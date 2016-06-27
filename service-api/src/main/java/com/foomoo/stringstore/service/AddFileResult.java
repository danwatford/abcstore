package com.foomoo.stringstore.service;

import java.util.UUID;

/**
 * Result of adding a file to the file store.
 */
public class AddFileResult {

    private final UUID fileId;
    private final UUID requestId;

    private final String user;
    private final boolean existingFile;

    /**
     * Construct an AddFileResult representing the result of adding a file to the file store.
     *
     * @param fileId       The id of the add file.
     * @param requestId    The id of the request to add the file.
     * @param user         The user making the add file request.
     * @param existingFile Indicate whether the file already existed in the file store.
     */
    public AddFileResult(final UUID fileId, final UUID requestId, final String user, final boolean existingFile) {
        this.fileId = fileId;
        this.requestId = requestId;
        this.user = user;
        this.existingFile = existingFile;
    }

    /**
     * Get the user who made the request to add the file.
     *
     * @return The user.
     */
    public String getUser() {
        return user;
    }

    /**
     * Get the id of the file resulting from the add file request.
     *
     * @return The file id.
     */
    public UUID getFileId() {
        return fileId;
    }

    /**
     * Get the id of the add file request.
     *
     * @return The request id.
     */
    public UUID getRequestId() {
        return requestId;
    }

    /**
     * Did the file already exist in the file store.
     *
     * @return True if the file already existed, false otherwise.
     */
    public boolean isExistingFile() {
        return existingFile;
    }

}
