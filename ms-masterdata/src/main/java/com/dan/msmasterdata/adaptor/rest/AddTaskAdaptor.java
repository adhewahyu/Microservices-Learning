package com.dan.msmasterdata.adaptor.rest;

import com.dan.msmasterdata.model.request.task.AddTaskRequest;
import com.dan.shared.adaptor.RestAdaptor;
import com.dan.shared.model.request.EsbRequest;
import com.dan.shared.model.response.ValidationResponse;
import com.dan.shared.utility.CommonConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Component
@Slf4j
@RequiredArgsConstructor
public class AddTaskAdaptor extends RestAdaptor<AddTaskRequest, ValidationResponse> {

    @Value("${config.integration.ms-task.add-task}")
    private String addTaskUrl;

    @Value("${config.integration.ms-task.key}")
    private String msTaskInternalApiKey;

    private final RestTemplate restClientEnhance;

    @Override
    public ValidationResponse execute(AddTaskRequest addTaskRequest) {
        this.setEnableDebug(true);
        this.setHttpMethod(HttpMethod.POST);
        this.setRestTemplate(restClientEnhance);
        this.setUrl(addTaskUrl);
        try{
            ResponseEntity<String> responseEntity = super.getResponse(generatePayload(addTaskRequest));
            log.info("Response = {} - {}", responseEntity.getStatusCodeValue(), responseEntity.getBody());
            return ValidationResponse.builder().result(true).build();
        }catch (HttpClientErrorException | HttpServerErrorException hse){
            log.error("Http Client Error = {} - {}", hse.getRawStatusCode(), hse.getResponseBodyAsString());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, CommonConstants.ERR_MSG_SOMETHING_WRONG);
        }catch (ResourceAccessException rae){
            log.error("Resource Access Exception = {}", rae.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, CommonConstants.ERR_MSG_SOMETHING_WRONG);
        }
    }

    @Override
    protected EsbRequest generatePayload(AddTaskRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(CommonConstants.REQ_HEADER_APIKEY, msTaskInternalApiKey);
        return EsbRequest.builder()
                .payload(new HttpEntity(request, httpHeaders))
                .isPlain(true)
                .build();
    }
}
