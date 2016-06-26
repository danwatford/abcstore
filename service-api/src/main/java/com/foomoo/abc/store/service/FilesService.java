package com.foomoo.abc.store.service;

import java.util.List;
import java.util.UUID;

/**
 * Provides the files operations for the service.
 */
public interface FilesService {

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
    AddFileResult addFile(String user, String content);

    /**
     * Get summaries of the files most recently added to the store.
     *
     * @return The {@link List} of {@link FileSummary} for the recently added file.
     */
    List<FileSummary> getFiles();

    /**
     * Get the summary of the file in the store identified by the given file id.
     *
     * @param id The file id of the file to summarise.
     * @return The {@link FileSummary} of the file.
     * @throws FileNotFoundException If the file for the given file id cannot be found.
     */
    FileSummary getFile(UUID id) throws FileNotFoundException;

    /**
     * Get the content of the file in the store identified by the given file id.
     *
     * @param id The file id of the file to summarise.
     * @return The content of the file.
     * @throws FileNotFoundException If the file for the given file id cannot be found.
     */
    String getFileContent(UUID id) throws FileNotFoundException;

    /**
     * Get summaries of the add file requests most recently made against the store.
     *
     * @return The {@link List} of {@link RequestSummary} for the most recently made add file requests.
     */
    List<RequestSummary> getRequests();

}
