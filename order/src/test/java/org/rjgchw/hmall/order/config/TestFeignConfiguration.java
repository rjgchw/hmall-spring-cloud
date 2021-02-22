package org.rjgchw.hmall.order.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.cloud.netflix.ribbon.StaticServerList;
import org.springframework.context.annotation.Bean;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

/**
 * @author Huangw
 * @date 2021-02-21 19:49
 */
@TestConfiguration
public class TestFeignConfiguration {

    @Autowired
    private WireMockServer mockBooksServer;

    @Bean(initMethod = "start", destroyMethod = "stop")
    public WireMockServer mockBooksServer() {
        return new WireMockServer(options().dynamicPort());
    }

    @Bean
    public ServerList<Server> ribbonServerList() {
        TestPropertyValues.of("ribbon-service.storage=localhost:" + mockBooksServer.port());
        return new StaticServerList<>(
            new Server("localhost", mockBooksServer.port()));
    }
}
