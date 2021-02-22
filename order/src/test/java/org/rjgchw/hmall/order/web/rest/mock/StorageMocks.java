package org.rjgchw.hmall.order.web.rest.mock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.util.StreamUtils.copyToString;

/**
 * @author Huangw
 * @date 2021-02-21 19:57
 */
public class StorageMocks {

    public static void setupMockDeductResponse(WireMockServer mockServer) throws IOException {
        mockServer.stubFor(
            WireMock
                .post(WireMock.urlEqualTo("/api/storages/deduct"))
            .willReturn(
                WireMock.aResponse()
                    .withStatus(HttpStatus.OK.value())
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withBody(
                        copyToString(
                            StorageMocks.class.getClassLoader()
                                .getResourceAsStream("payload/post-deduct-response.json"), defaultCharset()))
            ));
    }
}
