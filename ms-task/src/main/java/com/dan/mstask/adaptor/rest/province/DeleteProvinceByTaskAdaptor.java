package com.dan.mstask.adaptor.rest.province;

import com.dan.mstask.model.request.province.DeleteProvinceByTaskRequest;
import com.dan.mstask.model.request.province.UpdateProvinceByTaskRequest;
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
public class DeleteProvinceByTaskAdaptor extends RestAdaptor<DeleteProvinceByTaskRequest, ValidationResponse> {

    @Value("${config.integration.ms-masterdata.delete-province-by-task}")
    private String deleteProvinceByTaskUrl;

    @Value("${config.integration.ms-masterdata.key}")
    private String msMasterDataInternalApiKey;

    private final RestTemplate restClientEnhance;

    @Override
    public ValidationResponse execute(DeleteProvinceByTaskRequest request) {
        this.setEnableDebug(true);
        this.setHttpMethod(HttpMethod.POST);
        this.setRestTemplate(restClientEnhance);
        this.setUrl(deleteProvinceByTaskUrl);
        try{
            ResponseEntity<String> responseEntity = super.getResponse(generatePayload(request));
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
    protected EsbRequest generatePayload(DeleteProvinceByTaskRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(CommonConstants.REQ_HEADER_APIKEY, msMasterDataInternalApiKey);
        return EsbRequest.builder()
                .payload(new HttpEntity(request, httpHeaders))
                .isPlain(true)
                .build();
    }
}
