package org.rjgchw.hmall.storage.pact.provider;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Consumer;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import org.apache.http.HttpRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rjgchw.hmall.storage.ContractTest;
import org.springframework.boot.web.server.LocalServerPort;

/**
 * @author Huangw
 * @date 2021-02-22 10:47
 */
@Provider("storageProvider")
@Consumer("storageConsumer")
@ContractTest
@PactFolder("src/test/resources/pacts")
public class PactStorageProviderTest {

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context, HttpRequest request) {
        request.addHeader("Authorization", "bearer admin");
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
