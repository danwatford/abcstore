# ABC File Store
Service for storing ABC notation files within a MongoDB database.

Attempts to add a duplicate file are successful, but the ID of the original file is returned. Duplicate files are
detected based on the hash of their content.

All requests to upload a file, whether new or duplicate, result in a new request ID being returned.

Summaries of requests or files can be retrieved by ID.

File content can be retrieved by ID.

No processing of the file content is performed.

Although intended for storing ABC files, this service essentially stores any received file content as strings and
doesn't place any restriction on the files added to storage.

# Building
This service can be built using maven.

# Deployment
This service can be deployed as a servlet using the built WAR file, or by running as a Grizzly HTTP Server by
executing class com.foomoo.abc.store.app.App.
