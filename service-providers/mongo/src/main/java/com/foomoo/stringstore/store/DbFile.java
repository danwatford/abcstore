package com.foomoo.stringstore.store;

import java.util.UUID;

/**
 * Represents the data stored in the files collection in the MongoDB database.
 */
public class DbFile {

    private final UUID id;
    private final String user;
    private final int size;
    private final long epoch;
    private final String hash;
    private final String content;

    /**
     * Construct a DbFile representing a document in the files collection in the MongoDB database.
     *
     * @param id      The file id.
     * @param user    The user who added the file to the store.
     * @param size    The size of the file content.
     * @param epoch   The unix time that the file was added to the store.
     * @param hash    The hash of the file contents.
     * @param content The file content.
     */
    public DbFile(final UUID id, final String user, final int size, final long epoch,
                  final String hash, final String content) {
        this.id = id;
        this.user = user;
        this.size = size;
        this.epoch = epoch;
        this.hash = hash;
        this.content = content;
    }

    /**
     * Get the file id.
     *
     * @return The file id.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Get the user who added the file.
     *
     * @return The user.
     */
    public String getUser() {
        return user;
    }

    /**
     * Get the size of the file content.
     *
     * @return The size.
     */
    public int getSize() {
        return size;
    }

    /**
     * Get the unix time that the file was created.
     *
     * @return The file creation time.
     */
    public long getEpoch() {
        return epoch;
    }

    /**
     * Get the hash of the file content.
     *
     * @return The hash.
     */
    public String getHash() {
        return hash;
    }

    /**
     * Get the file content.
     *
     * @return The file content.
     */
    public String getContent() {
        return content;
    }
}
