package com.foomoo.stringstore.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Provides the strings operations for the service.
 */
public interface StringsService {

    /**
     * Adds a string to the store based on the given username and content.
     * <p>
     * The hash and length of the content is used to determine if the string is already present in the store. If already
     * present then the string id of the existing string is included in the response, otherwise a new string is added.
     * <p>
     * Regardless of whether a new string is created or an existing string is found, a new request is recorded and the
     * request id is included in the response.
     *
     * @param user    The user adding the string to the store.
     * @param content The content to be added to the store.
     * @return An {@link AddStringResult} describing the result of the add string operation.
     */
    AddStringResult addString(String user, String content);

    /**
     * Get summaries of the strings most recently added to the store.
     *
     * @return The {@link List} of {@link StringSummary} for the recently added string.
     */
    List<StringSummary> getStringSummaries();

    /**
     * Get summaries of all the strings in the store.
     *
     * @return The {@link List} of {@link StringSummary} for all strings in the store.
     */
    List<StringSummary> getAllStringSummaries();

    /**
     * Get the summary of the string in the store identified by the given string id.
     *
     * @param id The string id of the string to summarise.
     * @return The {@link StringSummary} of the string.
     * @throws StringNotFoundException If the string for the given string id cannot be found.
     */
    StringSummary getStringSummary(UUID id) throws StringNotFoundException;

    /**
     * Get the content of the string in the store identified by the given string id.
     *
     * @param id The string id of the string to summarise.
     * @return The content of the string.
     * @throws StringNotFoundException If the string for the given string id cannot be found.
     */
    String getStringContent(UUID id) throws StringNotFoundException;

    /**
     * Get the content of the strings identified by the given string ids. Strings will be returned in a map keyed by the
     * string id. Any strings that cannot be found by id will be absent from the returned list.
     *
     * @param ids The set of string ids to retrieve content for.
     * @return Map of string ids to string content for the found strings.
     */
    Map<UUID, String> getStringsContent(UUID... ids);

    /**
     * Get summaries of the add string requests most recently made against the store.
     *
     * @return The {@link List} of {@link RequestSummary} for the most recently made add string requests.
     */
    List<RequestSummary> getRequests();

}
