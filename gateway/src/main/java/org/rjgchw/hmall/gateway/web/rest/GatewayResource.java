package org.rjgchw.hmall.gateway.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.List;

/**
 *
 * @author Huangw
 * @date 2021-02-23 17:35
 */
@RestController
public class GatewayResource {

    private final SwaggerResourcesProvider swaggerResourcesProvider;

    public GatewayResource(SwaggerResourcesProvider swaggerResourcesProvider) {
        this.swaggerResourcesProvider = swaggerResourcesProvider;
    }

    /**
     * Swagger资源配置，微服务中这各个服务的api-docs信息
     */
    @GetMapping("/swagger-resources")
    public Mono<ResponseEntity<List<SwaggerResource>>> swaggerResources() {
        return Mono.just((new ResponseEntity<>(swaggerResourcesProvider.get(), HttpStatus.OK)));
    }
}
