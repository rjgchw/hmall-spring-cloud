package org.rjgchw.hmall.storage;

import org.junit.jupiter.api.extension.ExtendWith;
import org.rjgchw.hmall.common.test.container.RedisTestContainerExtension;
import org.rjgchw.hmall.storage.config.IntegrationTestConfiguration;
import org.rjgchw.hmall.storage.config.PactTestConfiguration;
import org.rjgchw.hmall.storage.config.TestSecurityConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Base composite annotation for contract tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { StorageApp.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {TestSecurityConfiguration.class, PactTestConfiguration.class})
@ExtendWith(RedisTestContainerExtension.class)
public @interface ContractTest {
}
