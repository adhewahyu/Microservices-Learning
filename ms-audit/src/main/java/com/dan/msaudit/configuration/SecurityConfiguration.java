package com.dan.msaudit.configuration;

import com.dan.shared.utility.CommonConstants;
import com.dan.shared.utility.PermissionUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

@Configuration
@RequiredArgsConstructor
@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration{

    private static final String[] ignoreApis = {
            "/swagger",
            "/webjars",
            "/api-docs",
    };

    @Value("${config.internal.api.key}")
    private String internalApiKey;

    private final PermissionUtility permissionUtility;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        permissionUtility.doInitStatelessHttpSecurity(http, "/v**");
        http.addFilterBefore(serviceFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    private Filter serviceFilter() {

        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                Boolean ignoreApis = checkIgnoredApis(request);
                Boolean validApiKey = StringUtils.isNotEmpty(request.getHeader(CommonConstants.REQ_HEADER_APIKEY)) && isValidApiKey(request);
                if(ignoreApis || validApiKey){
                    SecurityContextHolder.getContext().setAuthentication(null);
                    filterChain.doFilter(request, response);
                    return;
                }
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, CommonConstants.ERR_MSG_UNAUTHORIZED);
            }
        };
    }

    private Boolean checkIgnoredApis(HttpServletRequest request){
        AtomicReference<Boolean> isIgnored = new AtomicReference<>();
        Arrays.stream(ignoreApis).filter(url -> request.getRequestURI().contains(url))
                .findAny()
                .ifPresentOrElse(
                        data -> isIgnored.set(true),
                        () -> isIgnored.set(false)
                );
        return isIgnored.get();
    }

    private Boolean isValidApiKey(HttpServletRequest request){
        String apiKey = request.getHeader(CommonConstants.REQ_HEADER_APIKEY);
        if(StringUtils.isNotEmpty(apiKey) && apiKey.equals(internalApiKey)){
            log.info("request came from another micro service");
            return true;
        }
        return false;
    }

}
