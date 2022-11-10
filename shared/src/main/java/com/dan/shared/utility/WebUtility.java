package com.dan.shared.utility;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class WebUtility {

    private String cookieHeader = "Set-Cookie";
    private String secureCookie = "Secure";
    private String httpOnlyCookie = "HttpOnly";
    private String sameSite = "SameSite";
    private String pathCookie = "Path=/";
    private String maxAgeCookie = "Max-Age";
    private String expires = "Expires=T";

    public void doWriteCookie(HttpServletResponse response,
                              String cookieName,
                              String cookieValue,
                              Integer expiry,
                              Boolean secure,
                              Boolean httpOnly){
        if(StringUtils.isNotEmpty(response.getHeader(cookieHeader)) && response.getHeader(cookieHeader).contains(cookieName)){
            response.setHeader(cookieHeader, getCookieString(cookieName, cookieValue, expiry, secure, httpOnly, false));
        }else{
            response.addHeader(cookieHeader, getCookieString(cookieName, cookieValue, expiry, secure, httpOnly, false));
        }
    }

    public void doWriteCookie(HttpServletResponse response,
                              String cookieName,
                              String cookieValue,
                              Integer expiry,
                              Boolean secure,
                              Boolean httpOnly,
                              Boolean sameSiteNone) {
        if(StringUtils.isNotEmpty(response.getHeader(cookieHeader)) && response.getHeader(cookieHeader).contains(cookieName)){
            response.setHeader(cookieHeader, getCookieString(cookieName, cookieValue, expiry, secure, httpOnly, sameSiteNone));
        }else{
            response.addHeader(cookieHeader, getCookieString(cookieName, cookieValue, expiry, secure, httpOnly, sameSiteNone));
        }
    }

    private String getCookieString(String cookieName, String cookieValue,
                                   Integer expiry,
                                   Boolean secure,
                                   Boolean httpOnly,
                                   Boolean sameSiteNone){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(cookieName).append("=").append(cookieValue).append(";");
        stringBuilder.append(" ").append(pathCookie).append(";");
        if(sameSiteNone){
            stringBuilder.append(" ").append(sameSite).append("=").append("None").append(";");
        }
        if(secure || sameSiteNone){
            stringBuilder.append(" ").append(secureCookie).append(";");
        }
        if(httpOnly){
            stringBuilder.append(" ").append(httpOnlyCookie).append(";");
        }
        if(ObjectUtils.isNotEmpty(expiry)){
            stringBuilder.append(" ").append(maxAgeCookie).append("=").append(expiry).append(";");
            stringBuilder.append(" ").append(expires).append(";");
        }
        log.info("Set Cookie Value = {}", stringBuilder);
        return stringBuilder.toString();
    }

    public void doDeleteCookie( HttpServletResponse response, Boolean secure, Boolean httpOnly, String cookieName){
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setSecure(secure);
        cookie.setHttpOnly(httpOnly);
        response.addCookie(cookie);
    }

    public String getCookie( HttpServletRequest request,  String cookieName){
        Cookie cookie = WebUtils.getCookie(request, cookieName);
        if(cookie != null){
            return cookie.getValue();
        }
        return null;
    }
    
}
