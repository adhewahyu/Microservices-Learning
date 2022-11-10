package com.dan.msgateway.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Data
@Configuration
@ConfigurationProperties(value = "client")
public class CorsFilterConfiguration extends CorsConfiguration {

    private String[] endpoints;

    @Value("${config.cors.maxAge}")
    private String corsMaxAge;

    @Bean
    public CorsWebFilter corsFilter(){
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Arrays.asList(endpoints));
        config.addAllowedHeader("*");
        config.setMaxAge(Long.parseLong(corsMaxAge));
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }

}
