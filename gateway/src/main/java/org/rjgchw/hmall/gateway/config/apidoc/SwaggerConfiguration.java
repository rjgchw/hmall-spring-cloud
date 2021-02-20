package org.rjgchw.hmall.gateway.config.apidoc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Huangw
 * @date 2021-02-20 11:37
 */
@Component
@Primary
public class SwaggerConfiguration implements SwaggerResourcesProvider {

    private final Logger log = LoggerFactory.getLogger(SwaggerConfiguration.class);

    private final RouteLocator routeLocator;
    private final DiscoveryClient discoveryClient;

    @Value("${spring.application.name}")
    private String appName;

    public SwaggerConfiguration(RouteLocator routeLocator, DiscoveryClient discoveryClient) {
        this.routeLocator = routeLocator;
        this.discoveryClient = discoveryClient;
    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        //获取所有路由的ID
        routeLocator.getRoutes().subscribe(route -> {
            String predicate = route.getPredicate().toString();
            String path = predicate.substring(predicate.indexOf("[") + 1, predicate.indexOf("]"));
            String serviceId = route.getId().substring(route.getId().indexOf("_") + 1).toLowerCase();
            // Exclude gateway app from routes
            if (!serviceId.equalsIgnoreCase(appName)) {
                SwaggerResource resource = new SwaggerResource();
                resource.setName(serviceId);
                resource.setLocation(path.replace("**", "v3/api-docs"));
                resource.setSwaggerVersion("3.0");
                resources.add(resource);
            }
        });

        return resources;
    }
}
