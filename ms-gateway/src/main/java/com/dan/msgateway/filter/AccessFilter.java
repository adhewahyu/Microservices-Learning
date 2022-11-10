package com.dan.msgateway.filter;

import com.dan.msgateway.configuration.GatewayRouteProperties;
import com.dan.msgateway.model.response.CustomRouteResponse;
import com.dan.shared.utility.CommonConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccessFilter implements GlobalFilter, Ordered {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    private final GatewayRouteProperties gatewayRouteProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String filterRequest = String.format("%s request to %s", request.getMethod(), request.getURI());
        log.info("AccessFilter.run = {}", filterRequest);
        log.info("Cek IP Client = {}", this.getClientIP(request));

        if(request.getURI().toString().contains("/swagger") && !activeProfile.equals("dev")){
            log.error("Error accessing swagger in non dev environment");
            doThrow401(filterRequest);
        }

        if(StringUtils.isNotEmpty(request.getHeaders().getFirst(CommonConstants.REQ_HEADER_APIKEY))
                && !this.isValidMSKey(request.getHeaders().getFirst(CommonConstants.REQ_HEADER_APIKEY))){
            log.info("API Key is not valid");
            doThrow401(filterRequest);
        }
        log.info("Pre Global Filter - executed");
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    private String getClientIP(ServerHttpRequest request) {
        final String xfHeader = request.getHeaders().getFirst("X-Forwarded-For");
        if (xfHeader == null) {
            return Objects.requireNonNull(request.getRemoteAddress()).toString();
        }
        return xfHeader.split(",")[0];
    }

    private void doThrow401(String request){
        log.error("Throwing Unauthorized when access = {}", request);
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, CommonConstants.ERR_MSG_UNAUTHORIZED);
    }

    private Boolean isValidMSKey(String apiKey){
        if(activeProfile.equals("dev")) {
            return this.isValidMSInternalKey(apiKey);
        }
        return false;
    }

    private Boolean isValidMSInternalKey(String apiKey) {
        List<String> internalApiKeys = gatewayRouteProperties.getRoutes()
                .stream()
                .map(CustomRouteResponse::getApiKey)
                .collect(Collectors.toList());
        return internalApiKeys.contains(apiKey);
    }
}
