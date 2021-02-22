package org.rjgchw.hmall.common.test.container;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.GenericContainer;

import java.util.concurrent.atomic.AtomicBoolean;

public class RedisTestContainerExtension implements BeforeAllCallback {

    private static final AtomicBoolean started = new AtomicBoolean(false);

    private static final GenericContainer redis = new GenericContainer("redis:6.0.10").withExposedPorts(6379);

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        if (!started.get()) {
            redis.start();
            System.setProperty("jhipster.cache.redis.server", "redis://" + redis.getContainerIpAddress() + ":" + redis.getMappedPort(6379));
            started.set(true);
        }
    }
}
