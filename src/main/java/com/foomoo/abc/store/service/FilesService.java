package com.foomoo.abc.store.service;

import com.foomoo.abc.store.message.AddFileResult;
import com.foomoo.abc.store.message.FileSummary;
import com.foomoo.abc.store.message.RequestSummary;
import com.foomoo.abc.store.store.DbFile;
import com.foomoo.abc.store.store.DbRequest;
import com.foomoo.abc.store.store.MongoFileStoreClient;
import com.google.common.collect.Iterables;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Provides the files operations for the service.
 */
public class FilesService {

    private final MongoFileStoreClient mongoFileStoreClient = MongoFileStoreClient.instance;

    /**
     * Adds a file to the store based on the given username and content.
     * <p>
     * The hash and length of the content is used to determine if the file is already present in the store. If already
     * present then the file id of the existing file is included in the response, otherwise a new file is added.
     * <p>
     * Regardless of whether a new file is created or an existing file is found, a new request is recorded and the
     * request id is included in the response.
     *
     * @param user    The user adding the file to the store.
     * @param content The content to be added to the store.
     * @return An {@link AddFileResult} describing the result of the add file operation.
     */
    public AddFileResult addFile(final String user, final String content) {

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

        return new AddFileResult(fileId, requestId, user, existingFile);
    }

    /**
     * Get summaries of the files most recently added to the store.
     *
     * @return The {@link List} of {@link FileSummary} for the recently added file.
     */
    public List<FileSummary> getFiles() {

        final List<DbFile> recentFiles = mongoFileStoreClient.getRecentFiles(2);

        return recentFiles
                .stream()
                .map(FilesService::dbFileToFileSummary)
                .collect(Collectors.toList());
    }

    /**
     * Get the summary of the file in the store identified by the given file id.
     *
     * @param id The file id of the file to summarise.
     * @return The {@link FileSummary} of the file.
     * @throws FileNotFoundException If the file for the given file id cannot be found.
     */
    public FileSummary getFile(final UUID id) throws FileNotFoundException {
        final DbFile dbFile = mongoFileStoreClient.findFileById(id);

        return dbFileToFileSummary(dbFile);
    }

    /**
     * Get the content of the file in the store identified by the given file id.
     *
     * @param id The file id of the file to summarise.
     * @return The content of the file.
     * @throws FileNotFoundException If the file for the given file id cannot be found.
     */
    public String getFileContent(final UUID id) throws FileNotFoundException {
        final DbFile dbFile = mongoFileStoreClient.findFileById(id);

        return dbFile.getContent();
    }

    /**
     * Get summaries of the add file requests most recently made against the store.
     *
     * @return The {@link List} of {@link RequestSummary} for the most recently made add file requests.
     */
    public List<RequestSummary> getRequests() {
        final List<DbRequest> recentRequests = mongoFileStoreClient.getRecentRequests(2);

        return recentRequests
                .stream()
                .map(dbRequest -> new RequestSummary(dbRequest.getId(), dbRequest.getFileId(), dbRequest.getEpoch(), dbRequest.getUser()))
                .collect(Collectors.toList());
    }

    /**
     * Creates a {@link FileSummary} for the given {@link DbFile} object.
     *
     * @param dbFile The object to convert.
     * @return The created {@link FileSummary}.
     */
    private static FileSummary dbFileToFileSummary(final DbFile dbFile) {
        return new FileSummary(dbFile.getId(), dbFile.getEpoch(), dbFile.getSize());
    }

}
