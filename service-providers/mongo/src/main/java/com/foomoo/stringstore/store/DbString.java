package com.foomoo.stringstore.store;

import java.util.UUID;

/**
 * Represents the data stored in the strings collection in the MongoDB database.
 */
public class DbString {

    private final UUID id;
    private final String user;
    private final int size;
    private final long epoch;
    private final String hash;
    private final String content;

    /**
     * Construct a DbString representing a document in the strings collection in the MongoDB database.
     *
     * @param id      The string id.
     * @param user    The user who added the string to the store.
     * @param size    The size of the string content.
     * @param epoch   The unix time that the string was added to the store.
     * @param hash    The hash of the string contents.
     * @param content The string content.
     */
    public DbString(final UUID id, final String user, final int size, final long epoch,
                    final String hash, final String content) {
        this.id = id;
        this.user = user;
        this.size = size;
        this.epoch = epoch;
        this.hash = hash;
        this.content = content;
    }

    /**
     * Get the string id.
     *
     * @return The string id.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Get the user who added the string.
     *
     * @return The user.
     */
    public String getUser() {
        return user;
    }

    /**
     * Get the size of the string content.
     *
     * @return The size.
     */
    public int getSize() {
        return size;
    }

    /**
     * Get the unix time that the string was created.
     *
     * @return The string creation time.
     */
    public long getEpoch() {
        return epoch;
    }

    /**
     * Get the hash of the string content.
     *
     * @return The hash.
     */
    public String getHash() {
        return hash;
    }

    /**
     * Get the string content.
     *
     * @return The string content.
     */
    public String getContent() {
        return content;
    }
}
