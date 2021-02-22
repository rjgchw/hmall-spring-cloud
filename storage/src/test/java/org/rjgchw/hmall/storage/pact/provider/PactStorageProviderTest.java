package org.rjgchw.hmall.storage.pact.provider;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Consumer;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.VersionSelector;
import org.apache.http.HttpRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rjgchw.hmall.storage.IntegrationTest;
import org.springframework.boot.web.server.LocalServerPort;

/**
 * @author Huangw
 * @date 2021-02-22 10:47
 */
@Provider("storageProvider")
@Consumer("storageConsumer")
@PactBroker(
    host = "localhost",
    port = "8282",
    consumerVersionSelectors = {
        @VersionSelector(tag = "dev"),
        @VersionSelector(tag = "master"),
        @VersionSelector(tag = "test")
    }
)
@IntegrationTest
//@PactFolder("pacts")
public class PactStorageProviderTest {

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context, HttpRequest request) {
        context.verifyInteraction();
    }

    @LocalServerPort
    private int port;

    @BeforeAll
    static void enablePublishingPact() {
        System.setProperty("pact.verifier.publishResults", "true");
    }

    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", port));
    }

    @State("deduct storage from provider")
    public void deductStorageProvider() {
    }
}
