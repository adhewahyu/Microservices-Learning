package com.dan.msmasterdata.configuration;

import com.dan.shared.utility.CommonConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.WebFilter;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class SecurityConfiguration {

    private static final String[] ignoreApis = {
            "/swagger",
            "/webjars",
            "/api-docs",
    };

    @Value("${config.internal.api.key}")
    private String internalApiKey;

    @Bean
    public WebFilter serviceFilter(){
        return (exchange, chain) -> {
            Boolean ignoreApis = checkIgnoredApis(exchange.getRequest());
            Boolean validApiKey = StringUtils.isNotEmpty(exchange.getRequest().getHeaders().getFirst(CommonConstants.REQ_HEADER_APIKEY)) && isValidApiKey(exchange.getRequest());
            if(ignoreApis || validApiKey){
                return chain.filter(exchange);
            }else{
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, CommonConstants.ERR_MSG_UNAUTHORIZED);
            }
        };
    }

    private Boolean checkIgnoredApis(ServerHttpRequest request){
        AtomicReference<Boolean> isIgnored = new AtomicReference<>();
        Arrays.stream(ignoreApis).filter(url -> request.getURI().toString().contains(url))
                .findAny()
                .ifPresentOrElse(
                        data -> isIgnored.set(true),
                        () -> isIgnored.set(false)
                );
        return isIgnored.get();
    }

    private Boolean isValidApiKey(ServerHttpRequest request){
        String apiKey = request.getHeaders().getFirst(CommonConstants.REQ_HEADER_APIKEY);
        if(StringUtils.isNotEmpty(apiKey) && apiKey.equals(internalApiKey)){
            log.info("request came from another micro service");
            return true;
        }
        return false;
    }

}
