package com.foomoo.stringstore.service;

import com.foomoo.stringstore.store.MongoFileStoreClient;

/**
 * Mongo implementation of the status service.
 */
public class MongoStatusService implements StatusService {

    private final MongoFileStoreClient mongoFileStoreClient = MongoFileStoreClient.instance;

    /**
     * Get the status of the service by reporting the number of files and requests stored. Any problems accessing the
     * database will result in a {@link com.mongodb.MongoTimeoutException}.
     *
     * @return The status of the service.
     */
    @Override
    public Status getStatus() {
        return new Status(mongoFileStoreClient.getFilesCount(), mongoFileStoreClient.getRequestCount());
    }

}
