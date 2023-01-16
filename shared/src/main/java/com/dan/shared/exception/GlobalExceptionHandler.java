package com.dan.shared.exception;

import com.dan.shared.utility.CommonConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final DefaultErrorAttributes defaultErrorAttributes;

    @ExceptionHandler(value = {ResponseStatusException.class})
    protected ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex, WebRequest request) {
        log.error("Custom Handler Response Status Exception : {}", ex.getMessage());
        Map<String, Object>  data = getBodyData(ex, request);
        return ResponseEntity.status(ex.getStatus()).body(data);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException ex,WebRequest request) {
        log.error("Custom Handler Runtime Exception : {}", ex.getMessage());
        Map<String, Object>  data = getBodyData(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, CommonConstants.ERR_MSG_SOMETHING_WRONG), request);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(data);
    }

    private Map<String,Object> getBodyData(ResponseStatusException rse, WebRequest request){
        Map<String, Object>  data = defaultErrorAttributes.getErrorAttributes(request, ErrorAttributeOptions.defaults());
        data.put("status", rse.getStatus().value());
        data.put("error", rse.getStatus().getReasonPhrase());
        data.put("message", rse.getReason());
        data.put("path", ((ServletWebRequest)request).getRequest().getRequestURI());
        return data;
    }

}
