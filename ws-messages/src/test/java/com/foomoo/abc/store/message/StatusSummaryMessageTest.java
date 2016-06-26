package com.foomoo.abc.store.message;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.IOException;

import static org.junit.Assert.assertThat;

/**
 * Serialization and Deserialization tests for {@link StatusSummaryMessage}
 */
public class StatusSummaryMessageTest {
    private static final long TEST_FILES_COUNT = 1;
    private static final long TEST_REQUESTS_COUNT = 2;

    private static final StatusSummaryMessage TEST_STATUS_SUMMARY = new StatusSummaryMessage(TEST_FILES_COUNT, TEST_REQUESTS_COUNT);
    private static final JsonObject TEST_JSON = Json.createObjectBuilder().add("files", TEST_FILES_COUNT).add("requests", TEST_REQUESTS_COUNT).build();

    @Test
    public void serializedIncludesFilesCount() throws Exception {
        final JsonNode jsonNode = objectToJsonNode(TEST_STATUS_SUMMARY);

        final JsonNode filesNode = jsonNode.get("files");
        assertThat(filesNode, Matchers.notNullValue());
        assertThat(filesNode.asLong(), Matchers.equalTo(TEST_FILES_COUNT));
    }

    @Test
    public void serializedIncludesRequestsCount() {
        final JsonNode jsonNode = objectToJsonNode(TEST_STATUS_SUMMARY);

        final JsonNode filesNode = jsonNode.get("requests");
        assertThat(filesNode, Matchers.notNullValue());
        assertThat(filesNode.asLong(), Matchers.equalTo(TEST_REQUESTS_COUNT));
    }

    @Test
    public void deserializedIncludeFilesCount() {
        final StatusSummaryMessage statusSummaryMessage = jsonToStatus(TEST_JSON.toString());

        assertThat(statusSummaryMessage.getFiles(), Matchers.equalTo(TEST_FILES_COUNT));
    }

    @Test
    public void deserializedIncludeRequestsCount() {
        final StatusSummaryMessage statusSummaryMessage = jsonToStatus(TEST_JSON.toString());

        assertThat(statusSummaryMessage.getRequests(), Matchers.equalTo(TEST_REQUESTS_COUNT));
    }


    private JsonNode objectToJsonNode(final Object object) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readTree(mapper.writeValueAsString(object));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private StatusSummaryMessage jsonToStatus(final String jsonString) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonString, StatusSummaryMessage.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}