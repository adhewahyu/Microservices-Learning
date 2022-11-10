package com.dan.msaudit.exception;

import com.dan.shared.utility.CommonConstants;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@ControllerAdvice(basePackages = "com.dan.msaudit.controller")
@Slf4j
@RequiredArgsConstructor
public class CustomHandlerExceptionHandler extends ResponseEntityExceptionHandler {

    private final DefaultErrorAttributes defaultErrorAttributes;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Custom Handler HttpMessageNotReadableException : {}", ex.getMessage());
        Map<String, Object>  data = getBodyData(status, request, CommonConstants.ERR_MSG_SOMETHING_WRONG);
        return handleExceptionInternal(ex, data, headers, status, request);
    }

    @ExceptionHandler(value = InvalidFormatException.class)
    protected ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Custom Handler InvalidFormatException : {}", ex.getMessage());
        Map<String, Object>  data = getBodyData(status, request, CommonConstants.ERR_MSG_SOMETHING_WRONG);
        return handleExceptionInternal(ex, data, headers, status, request);
    }

    private Map<String,Object> getBodyData(HttpStatus status, WebRequest request, String errorMessage){
        Map<String, Object>  data = defaultErrorAttributes.getErrorAttributes(request, ErrorAttributeOptions.defaults());
        data.put("status", status.value());
        data.put("error", status.getReasonPhrase());
        data.put("message", errorMessage);
        data.put("path", ((ServletWebRequest)request).getRequest().getRequestURI());
        return data;
    }

}
