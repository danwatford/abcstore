package com.foomoo.stringstore.service;

import com.foomoo.stringstore.store.DbString;
import com.foomoo.stringstore.store.DbRequest;
import com.foomoo.stringstore.store.MongoStringStoreClient;
import com.google.common.collect.Iterables;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Mongo implementation of the string service.
 */
public class MongoStringsService implements StringsService {

    private final MongoStringStoreClient mongoStringStoreClient = MongoStringStoreClient.instance;

    @Override
    public AddStringResult addString(final String user, final String content) {

        final UUID requestId = UUID.randomUUID();
        final long epoch = Instant.now().toEpochMilli();

        final String stringSha1 = DigestUtils.sha1Hex(content);
        final List<DbString> foundDbStrings = mongoStringStoreClient.findStringBySizeHash(content.length(), stringSha1);

        final UUID stringId;
        final boolean existingString;
        if (foundDbStrings.isEmpty()) {
            stringId = UUID.randomUUID();
            existingString = false;
            final DbString dbString = new DbString(stringId, user, content.length(), epoch, stringSha1, content);

            mongoStringStoreClient.addString(dbString);
        } else {
            final DbString dbString = Iterables.getOnlyElement(foundDbStrings);
            stringId = dbString.getId();
            existingString = true;
        }

        final DbRequest dbRequest = new DbRequest(requestId, user, stringId, epoch);
        mongoStringStoreClient.addRequest(dbRequest);

        return new AddStringResult(stringId, requestId, user, existingString);
    }

    @Override
    public List<StringSummary> getStringSummaries() {

        final List<DbString> recentStrings = mongoStringStoreClient.getRecentStrings(ServiceConfiguration.RECENT_STRING_COUNT);

        return recentStrings
                .stream()
                .map(MongoStringsService::dbStringToStringSummary)
                .collect(Collectors.toList());
    }

    @Override
    public List<StringSummary> getAllStringSummaries() {
        final List<DbString> allStrings = mongoStringStoreClient.getRecentStrings(ServiceConfiguration.RECENT_STRING_COUNT);

        return allStrings
                .stream()
                .map(MongoStringsService::dbStringToStringSummary)
                .collect(Collectors.toList());
    }

    @Override
    public StringSummary getStringSummary(final UUID id) throws StringNotFoundException {
        final DbString dbString = mongoStringStoreClient.findStringById(id);

        return dbStringToStringSummary(dbString);
    }

    @Override
    public String getStringContent(final UUID id) throws StringNotFoundException {
        final DbString dbString = mongoStringStoreClient.findStringById(id);

        return dbString.getContent();
    }

    @Override
    public Map<UUID, String> getStringsContent(final UUID... ids) {
        final List<DbString> dbStrings = mongoStringStoreClient.findStringsByIds(ids);

        return dbStrings
                .stream()
                .collect(Collectors.toMap(DbString::getId, DbString::getContent));
    }

    @Override
    public List<RequestSummary> getRequests() {
        final List<DbRequest> recentRequests = mongoStringStoreClient.getRecentRequests(ServiceConfiguration.RECENT_REQUEST_COUNT);

        return recentRequests
                .stream()
                .map(dbRequest -> new RequestSummary(dbRequest.getId(), dbRequest.getStringId(), dbRequest.getEpoch(), dbRequest.getUser()))
                .collect(Collectors.toList());
    }

    /**
     * Creates a {@link StringSummary} for the given {@link DbString} object.
     *
     * @param dbString The object to convert.
     * @return The created {@link StringSummary}.
     */
    private static StringSummary dbStringToStringSummary(final DbString dbString) {
        return new StringSummary(dbString.getId(), dbString.getEpoch(), dbString.getSize());
    }

}
