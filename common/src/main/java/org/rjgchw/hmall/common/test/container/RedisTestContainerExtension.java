package org.rjgchw.hmall.common.test.container;

import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.GenericContainer;

/**
 * @author Huangw
 * @date 2021-02-23 17:38
 */
public class RedisTestContainerExtension implements BeforeAllCallback {

    private static final AtomicBoolean STARTED = new AtomicBoolean(false);

    private static final GenericContainer REDIS = new GenericContainer("redis:6.0.10")
        .withExposedPorts(6379);

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        if (!STARTED.get()) {
            REDIS.start();
            System.setProperty("jhipster.cache.redis.server",
                "redis://" + REDIS.getContainerIpAddress() + ":" + REDIS.getMappedPort(6379));
            STARTED.set(true);
        }
    }
}
