# String Store
Libraries and web service for storing Strings within a MongoDB database.

Attempts to add a duplicate string are successful, but the ID of the original string is returned. Duplicate strings are
detected based on the hash of their content.

All requests to store a string, whether new or duplicate, result in a new request ID being returned.

Summaries of requests or strings can be retrieved by ID.

Strings can be retrieved by ID.

No processing of the string content is performed.

This project was originally intended for storing ABC Notation Files, but became general purpose string storage
when it was realised that the storage of the file content with minimal metadata all that was needed.

# Building
This service can be built using maven.

# Deployment
This service can be deployed as a servlet using the WAR file from the string-store-war module, or by running as a
Grizzly HTTP Server by running class com.foomoo.stringstore.app.App from the string-store-grizzly module (use
mvn exec:java).
