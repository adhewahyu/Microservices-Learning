package com.dan.msgateway.configuration;

import com.dan.msgateway.model.response.CustomRouteResponse;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "gateway")
public class GatewayRouteProperties {

    private List<CustomRouteResponse> routes;

}
