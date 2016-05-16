package com.foomoo.abc.store.service;

import com.foomoo.abc.store.message.Status;
import com.foomoo.abc.store.store.MongoFileStoreClient;

/**
 * Provides the status of the service.
 */
public class StatusService {

    private final MongoFileStoreClient mongoFileStoreClient = MongoFileStoreClient.instance;

    /**
     * Get the status of the service by reporting the number of files and requests stored. Any problems accessing the
     * database will result in a {@link com.mongodb.MongoTimeoutException}.
     *
     * @return The status of the service.
     */
    public Status getStatus() {
        return new Status(mongoFileStoreClient.getFilesCount(), mongoFileStoreClient.getRequestCount());
    }

}
