package com.dan.msgateway.configuration;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@RequiredArgsConstructor
public class RouteConfiguration {

    private final GatewayRouteProperties gatewayRouteProperties;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        RouteLocatorBuilder.Builder routeLocatorBuilder = builder.routes();
        gatewayRouteProperties.getRoutes().forEach(customRoute -> routeLocatorBuilder.route(customRoute.getId(),
                route -> route.path(customRoute.getPath())
                        .uri(customRoute.getUrl())));
        return routeLocatorBuilder.build();
    }

}
