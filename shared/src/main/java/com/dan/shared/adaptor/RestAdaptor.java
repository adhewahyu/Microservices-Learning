package com.dan.shared.adaptor;

import com.alibaba.fastjson2.JSON;
import com.dan.shared.model.request.BaseRequest;
import com.dan.shared.model.request.EsbRequest;
import com.dan.shared.model.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Setter
@Getter
@Log4j2
public abstract class RestAdaptor <T extends BaseRequest, V extends BaseResponse> {

    protected String url;
    protected HttpMethod httpMethod;
    protected Class<V> response;
    protected RestTemplate restTemplate;
    protected Boolean enableDebug = false;

    protected ResponseEntity getResponse(EsbRequest request) {
        String requestUrl;
        if (ObjectUtils.isNotEmpty(request.getParams()) && !request.getParams().isEmpty()) {
            LinkedMultiValueMap<String, String> params = request.getParams();
            log.info("params = {}", params);
            UriComponents builder = UriComponentsBuilder.fromUriString(url).queryParams(params).build();
            requestUrl = builder.toUriString();
        } else {
            requestUrl = url;
        }
        if(getEnableDebug()){
            log.info("requestUrl = {}", requestUrl);
            log.info("requestPayload = {}", JSON.toJSONString(request.getPayload()));
        }
        ResponseEntity responseEntity = (request.getIsPlain()) ?
                this.restTemplate.exchange(requestUrl, httpMethod, request.getPayload(), String.class) :
                this.restTemplate.exchange(requestUrl, httpMethod, request.getPayload(), response);
        if(getEnableDebug()){
            log.info("response for {} = {}", url, responseEntity);
        }
        return responseEntity;
    }

    public abstract V execute(T request);
    protected abstract EsbRequest generatePayload(T request);
    
}