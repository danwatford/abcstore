package com.foomoo.stringstore.store;

import com.foomoo.stringstore.service.StringNotFoundException;
import com.google.common.collect.Iterables;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * The client for connecting to the MongoDB database and performing add and find operations.
 */
public class MongoStringStoreClient {

    public static final MongoStringStoreClient instance = new MongoStringStoreClient();

    final private MongoClient client = new MongoClient(DbConfiguration.HOSTNAME, DbConfiguration.PORT);
    final private MongoDatabase db = client.getDatabase(DbConfiguration.DATABASE);
    final private MongoCollection<Document> strings = db.getCollection(DbConfiguration.STRINGS_COLLECTION);
    final private MongoCollection<Document> requests = db.getCollection(DbConfiguration.REQUESTS_COLLECTION);

    /**
     * Get the number of documents in the strings collection.
     *
     * @return The number of documents.
     */
    public long getStringsCount() {
        return strings.count();
    }

    /**
     * Get the number of documents in the requests collection.
     *
     * @return The number of documents.
     */
    public long getRequestCount() {
        return requests.count();
    }

    /**
     * Finds documents in the strings collection matching the given size and SHA1 hash.
     *
     * @param size     The size property to search for.
     * @param sha1Hash The hash property to search for.
     * @return A {@link List} of {@link DbString} objects matching the requested size and hash.
     */
    public List<DbString> findStringBySizeHash(final int size, final String sha1Hash) {
        final Document queryDocument = new Document().append("size", size).append("hash", sha1Hash);

        final FindIterable<Document> foundStrings = strings.find(queryDocument).limit(1);

        return foundStrings.map(MongoStringStoreClient::documentToDbString).into(new ArrayList<>());
    }

    /**
     * Finds the document in the strings collection with the given id.
     *
     * @param id The id of the document to find.
     * @return The {@link DbString} found for the given id.
     * @throws StringNotFoundException If no document is found with the given id.
     */
    public DbString findStringById(final UUID id) throws StringNotFoundException {
        final Document queryDocument = new Document().append("_id", id);

        final FindIterable<Document> foundStrings = strings.find(queryDocument).limit(1);

        final ArrayList<DbString> dbStrings = foundStrings.map(MongoStringStoreClient::documentToDbString).into(new ArrayList<>());
        if (dbStrings.isEmpty()) {
            throw new StringNotFoundException(id);
        } else {
            return Iterables.getOnlyElement(dbStrings);
        }
    }

    /**
     * Finds the documents in the strings collection with the given ids. If a string cannot be found it will be absent
     * from the resulting list.
     *
     * @param ids The id of the documents to find.
     * @return The List of {@link DbString}s found for the given ids.
     */
    public List<DbString> findStringsByIds(final UUID... ids) {

        final BasicDBList idsList = new BasicDBList();
        idsList.addAll(Arrays.asList(ids));

        final Document queryDocument = new Document().append("_id", new BasicDBObject("$in", idsList));

        final FindIterable<Document> foundStrings = strings.find(queryDocument);

        return foundStrings.map(MongoStringStoreClient::documentToDbString).into(new ArrayList<>());
    }

    /**
     * Add a document to the strings collection based on the given {@link DbString}.
     *
     * @param string The {@link DbString} to base the document on.
     */
    public void addString(final DbString string) {

        final Document document = dbStringToDocument(string);

        strings.insertOne(document);
    }

    /**
     * Get the most recently added documents from the strings collection. The given limit specifies the maximum number
     * of documents to retrieve.
     *
     * @param limit The maximum number of documents to retrieve.
     * @return A {link List} of {@link DbString} objects based on documents retrieved from the strings collection.
     */
    public List<DbString> getRecentStrings(final int limit) {
        final FindIterable<Document> foundStrings = strings.find().sort(new Document("creation_epoch", -1)).limit(limit);

        return foundStrings.map(MongoStringStoreClient::documentToDbString).into(new ArrayList<>());
    }


    /**
     * Get all strings from the strings collection.
     *
     * @return A {link List} of {@link DbString} objects based on documents retrieved from the strings collection.
     */
    public List<DbString> getAllStrings() {

        final FindIterable<Document> foundStrings = strings.find();

        return foundStrings.map(MongoStringStoreClient::documentToDbString).into(new ArrayList<>());
    }

    /**
     * Add a document to the requests collection based on the given {@link DbRequest}.
     *
     * @param request The {@link DbRequest} to base the document on.
     */
    public void addRequest(final DbRequest request) {

        final Document document = dbRequestToDocument(request);

        requests.insertOne(document);
    }

    /**
     * Get the most recently added documents from the requests collection. The given limit specifies the maximum number
     * of documents to retrieve.
     *
     * @param limit The maximum number of documents to retrieve.
     * @return A {link List} of {@link DbRequest} objects based on documents retrieved from the requests collection.
     */
    public List<DbRequest> getRecentRequests(final int limit) {
        final FindIterable<Document> foundRequests = requests.find().sort(new Document("epoch", -1)).limit(limit);

        return foundRequests.map(MongoStringStoreClient::documentToDbRequest).into(new ArrayList<>());
    }

    /**
     * Convert the given {@link Document} from MongoDB to a {@link DbString} object.
     *
     * @param document The object to convert.
     * @return The created {@link DbString}.
     */
    private static DbString documentToDbString(final Document document) {

        final UUID id = (UUID) document.get("_id");
        final String user = document.getString("user");
        final String hash = document.getString("hash");
        final int size = document.getInteger("size");
        final long creationEpoch = document.getLong("creation_epoch");
        final String content = document.getString("content");

        return new DbString(id, user, size, creationEpoch, hash, content);
    }

    /**
     * Convert the given {@link DbString} to a {@link Document} for writing to MongoDB.
     *
     * @param dbString The object to convert.
     * @return The created {@link Document}.
     */
    private static Document dbStringToDocument(final DbString dbString) {
        return new Document()
                .append("_id", dbString.getId())
                .append("user", dbString.getUser())
                .append("hash", dbString.getHash())
                .append("size", dbString.getSize())
                .append("creation_epoch", dbString.getEpoch())
                .append("content", dbString.getContent());
    }

    /**
     * Convert the given {@link Document} from MongoDB to a {@link DbRequest} object.
     *
     * @param document The object to convert.
     * @return The created {@link DbRequest}.
     */
    private static DbRequest documentToDbRequest(final Document document) {

        final UUID id = (UUID) document.get("_id");
        final UUID stringId = (UUID) document.get("string_id");
        final String user = document.getString("user");
        final long epoch = document.getLong("epoch");

        return new DbRequest(id, user, stringId, epoch);
    }

    /**
     * Convert the given {@link DbRequest} to a {@link Document} for writing to MongoDB.
     *
     * @param dbRequest The object to convert.
     * @return The created {@link Document}.
     */
    private static Document dbRequestToDocument(final DbRequest dbRequest) {
        return new Document()
                .append("_id", dbRequest.getId())
                .append("string_id", dbRequest.getStringId())
                .append("user", dbRequest.getUser())
                .append("epoch", dbRequest.getEpoch());
    }

    // Private constructor.
    private MongoStringStoreClient() {
    }

}
