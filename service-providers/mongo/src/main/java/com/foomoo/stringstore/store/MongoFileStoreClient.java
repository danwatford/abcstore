package com.foomoo.stringstore.store;

import com.foomoo.stringstore.service.FileNotFoundException;
import com.google.common.collect.Iterables;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The client for connecting to the MongoDB database and performing add and find operations.
 */
public class MongoFileStoreClient {

    public static final MongoFileStoreClient instance = new MongoFileStoreClient();

    final private MongoClient client = new MongoClient(DbConfiguration.HOSTNAME, DbConfiguration.PORT);
    final private MongoDatabase db = client.getDatabase(DbConfiguration.DATABASE);
    final private MongoCollection<Document> files = db.getCollection(DbConfiguration.FILES_COLLECTION);
    final private MongoCollection<Document> requests = db.getCollection(DbConfiguration.REQUESTS_COLLECTION);

    /**
     * Get the number of documents in the files collection.
     *
     * @return The number of documents.
     */
    public long getFilesCount() {
        return files.count();
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
     * Finds documents in the files collection matching the given size and SHA1 hash.
     *
     * @param size     The size property to search for.
     * @param sha1Hash The hash property to search for.
     * @return A {@link List} of {@link DbFile} objects matching the requested size and hash.
     */
    public List<DbFile> findFileBySizeHash(final int size, final String sha1Hash) {
        final Document queryDocument = new Document().append("size", size).append("hash", sha1Hash);

        final FindIterable<Document> foundFiles = files.find(queryDocument).limit(1);

        return foundFiles.map(MongoFileStoreClient::documentToDbFile).into(new ArrayList<>());
    }

    /**
     * Finds the document in the files collection with the given id.
     *
     * @param id The id of the document to find.
     * @return The {@link DbFile} found for the given id.
     * @throws FileNotFoundException If no document is found with the given id.
     */
    public DbFile findFileById(final UUID id) throws FileNotFoundException {
        final Document queryDocument = new Document().append("_id", id);

        final FindIterable<Document> foundFiles = files.find(queryDocument).limit(1);

        final ArrayList<DbFile> dbFiles = foundFiles.map(MongoFileStoreClient::documentToDbFile).into(new ArrayList<>());
        if (dbFiles.isEmpty()) {
            throw new FileNotFoundException(id);
        } else {
            return Iterables.getOnlyElement(dbFiles);
        }
    }

    /**
     * Add a document to the files collection based on the given {@link DbFile}.
     *
     * @param file The {@link DbFile} to base the document on.
     */
    public void addFile(final DbFile file) {

        final Document document = dbFileToDocument(file);

        files.insertOne(document);
    }

    /**
     * Get the most recently added documents from the files collection. The given limit specifies the maximum number of
     * documents to retrieve.
     *
     * @param limit The maximum number of documents to retrieve.
     * @return A {link List} of {@link DbFile} objects based on documents retrieved from the files collection.
     */
    public List<DbFile> getRecentFiles(final int limit) {
        final FindIterable<Document> foundFiles = files.find().sort(new Document("creation_epoch", -1)).limit(limit);

        return foundFiles.map(MongoFileStoreClient::documentToDbFile).into(new ArrayList<>());
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

        return foundRequests.map(MongoFileStoreClient::documentToDbRequest).into(new ArrayList<>());
    }

    /**
     * Convert the given {@link Document} from MongoDB to a {@link DbFile} object.
     *
     * @param document The object to convert.
     * @return The created {@link DbFile}.
     */
    private static DbFile documentToDbFile(final Document document) {

        final UUID id = (UUID) document.get("_id");
        final String user = document.getString("user");
        final String hash = document.getString("hash");
        final int size = document.getInteger("size");
        final long creationEpoch = document.getLong("creation_epoch");
        final String content = document.getString("content");

        return new DbFile(id, user, size, creationEpoch, hash, content);
    }

    /**
     * Convert the given {@link DbFile} to a {@link Document} for writing to MongoDB.
     *
     * @param dbFile The object to convert.
     * @return The created {@link Document}.
     */
    private static Document dbFileToDocument(final DbFile dbFile) {
        return new Document()
                .append("_id", dbFile.getId())
                .append("user", dbFile.getUser())
                .append("hash", dbFile.getHash())
                .append("size", dbFile.getSize())
                .append("creation_epoch", dbFile.getEpoch())
                .append("content", dbFile.getContent());
    }

    /**
     * Convert the given {@link Document} from MongoDB to a {@link DbRequest} object.
     *
     * @param document The object to convert.
     * @return The created {@link DbRequest}.
     */
    private static DbRequest documentToDbRequest(final Document document) {

        final UUID id = (UUID) document.get("_id");
        final UUID fileId = (UUID) document.get("file_id");
        final String user = document.getString("user");
        final long epoch = document.getLong("epoch");

        return new DbRequest(id, user, fileId, epoch);
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
                .append("file_id", dbRequest.getFileId())
                .append("user", dbRequest.getUser())
                .append("epoch", dbRequest.getEpoch());
    }

    // Private constructor.
    private MongoFileStoreClient() {
    }

}
