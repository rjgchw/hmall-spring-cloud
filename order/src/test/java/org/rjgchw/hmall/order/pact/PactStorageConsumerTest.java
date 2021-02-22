package org.rjgchw.hmall.order.pact;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rjgchw.hmall.common.util.JsonUtil;
import org.rjgchw.hmall.order.client.request.StorageDeductRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Huangw
 * @date 2021-02-21 12:05
 */
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "storageProvider", port = "12345")
public class PactStorageConsumerTest {

    private static final String DEDUCT_STORAGE_URL = "/api/storages/deduct";

    @Pact(consumer = "storageConsumer")
    public RequestResponsePact deductStorageProvider(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json");

        return builder
            .given("deduct storage from provider")
            .uponReceiving("deduct storage from provider")
            .method("POST")
            .body(JsonUtil.nonDefaultMapper().toJson(new StorageDeductRequest(1L, 1)))
            .path(DEDUCT_STORAGE_URL)
            .willRespondWith()
            .headers(headers)
            .status(200)
            .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "deductStorageProvider")
    public void testDeductStorage(MockServer mockServer) throws IOException {
        HttpResponse httpResponse = Request.Post(mockServer.getUrl() + DEDUCT_STORAGE_URL)
            .bodyString(JsonUtil.nonDefaultMapper().toJson(new StorageDeductRequest(1L, 1)), ContentType.APPLICATION_JSON)
            .execute().returnResponse();

        assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(200);
    }
}
