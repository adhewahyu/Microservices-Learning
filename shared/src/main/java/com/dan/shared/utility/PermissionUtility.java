package com.dan.shared.utility;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.stereotype.Component;

@Component
public class PermissionUtility {

    public void doInitStatelessHttpSecurity(HttpSecurity http, String path) throws Exception{
        http.csrf().disable().authorizeRequests().antMatchers(path).authenticated();
    }

    public void doInitStatelessHttpSecurityWebFlux(ServerHttpSecurity http, String path) throws Exception{
        http.csrf().disable().authorizeExchange().pathMatchers(path).authenticated();
    }
    
}
