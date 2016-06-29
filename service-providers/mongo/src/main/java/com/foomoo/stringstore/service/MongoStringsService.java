package com.foomoo.stringstore.service;

import com.foomoo.stringstore.store.DbFile;
import com.foomoo.stringstore.store.DbRequest;
import com.foomoo.stringstore.store.MongoFileStoreClient;
import com.google.common.collect.Iterables;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Mongo implementation of the file service.
 */
public class MongoStringsService implements StringsService {

    private final MongoFileStoreClient mongoFileStoreClient = MongoFileStoreClient.instance;

    @Override
    public AddStringResult addString(final String user, final String content) {

        final UUID requestId = UUID.randomUUID();
        final long epoch = Instant.now().toEpochMilli();

        // Hash the content to determine a filename, then see if that filename already exists in the store.
        final String fileSha1 = DigestUtils.sha1Hex(content);
        final List<DbFile> foundDbFiles = mongoFileStoreClient.findFileBySizeHash(content.length(), fileSha1);

        final UUID fileId;
        final boolean existingFile;
        if (foundDbFiles.isEmpty()) {
            fileId = UUID.randomUUID();
            existingFile = false;
            final DbFile dbFile = new DbFile(fileId, user, content.length(), epoch, fileSha1, content);

            mongoFileStoreClient.addFile(dbFile);
        } else {
            final DbFile dbFile = Iterables.getOnlyElement(foundDbFiles);
            fileId = dbFile.getId();
            existingFile = true;
        }

        final DbRequest dbRequest = new DbRequest(requestId, user, fileId, epoch);
        mongoFileStoreClient.addRequest(dbRequest);

        return new AddStringResult(fileId, requestId, user, existingFile);
    }

    @Override
    public List<StringSummary> getStrings() {

        final List<DbFile> recentFiles = mongoFileStoreClient.getRecentFiles(ServiceConfiguration.RECENT_FILE_COUNT);

        return recentFiles
                .stream()
                .map(MongoStringsService::dbFileToFileSummary)
                .collect(Collectors.toList());
    }

    @Override
    public StringSummary getString(final UUID id) throws StringNotFoundException {
        final DbFile dbFile = mongoFileStoreClient.findFileById(id);

        return dbFileToFileSummary(dbFile);
    }

    @Override
    public String getStringContent(final UUID id) throws StringNotFoundException {
        final DbFile dbFile = mongoFileStoreClient.findFileById(id);

        return dbFile.getContent();
    }

    @Override
    public List<RequestSummary> getRequests() {
        final List<DbRequest> recentRequests = mongoFileStoreClient.getRecentRequests(ServiceConfiguration.RECENT_REQUEST_COUNT);

        return recentRequests
                .stream()
                .map(dbRequest -> new RequestSummary(dbRequest.getId(), dbRequest.getFileId(), dbRequest.getEpoch(), dbRequest.getUser()))
                .collect(Collectors.toList());
    }

    /**
     * Creates a {@link StringSummary} for the given {@link DbFile} object.
     *
     * @param dbFile The object to convert.
     * @return The created {@link StringSummary}.
     */
    private static StringSummary dbFileToFileSummary(final DbFile dbFile) {
        return new StringSummary(dbFile.getId(), dbFile.getEpoch(), dbFile.getSize());
    }

}
