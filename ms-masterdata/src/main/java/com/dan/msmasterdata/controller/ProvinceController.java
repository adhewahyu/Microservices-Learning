package com.dan.msmasterdata.controller;

import com.dan.msmasterdata.model.request.province.AddProvinceRequest;
import com.dan.msmasterdata.model.request.province.DeleteProvinceRequest;
import com.dan.msmasterdata.model.request.province.UpdateProvinceRequest;
import com.dan.msmasterdata.service.province.AddProvinceService;
import com.dan.msmasterdata.service.province.DeleteProvinceService;
import com.dan.msmasterdata.service.province.UpdateProvinceService;
import com.dan.shared.controller.BaseController;
import com.dan.shared.enums.MessageCode;
import com.dan.shared.model.response.RestResponse;
import com.dan.shared.model.response.ValidationResponse;
import com.dan.shared.utility.CommonConstants;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Api(tags = "Province APIs")
@RestController
@RequestMapping("/province")
@RequiredArgsConstructor
public class ProvinceController extends BaseController {

    private final AddProvinceService addProvinceService;
    private final UpdateProvinceService updateProvinceService;
    private final DeleteProvinceService deleteProvinceService;

    @PostMapping("/v1/add")
    public Mono<ResponseEntity<RestResponse>> addProvince(
            @RequestHeader(CommonConstants.REQ_HEADER_APIKEY) String apiKey,
            @RequestBody AddProvinceRequest addProvinceRequest){
        ValidationResponse validationResponse = addProvinceService.execute(addProvinceRequest);
        return Mono.just(new ResponseEntity<>( new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, MessageCode.OK.getValue(), validationResponse.getResult()), HttpStatus.OK));
    }

    @PostMapping("/v1/update")
    public Mono<ResponseEntity<RestResponse>> updateProvince(
            @RequestHeader(CommonConstants.REQ_HEADER_APIKEY) String apiKey,
            @RequestBody UpdateProvinceRequest updateProvinceRequest){
        ValidationResponse validationResponse = updateProvinceService.execute(updateProvinceRequest);
        return Mono.just(new ResponseEntity<>( new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, MessageCode.OK.getValue(), validationResponse.getResult()), HttpStatus.OK));
    }

    @PostMapping("/v1/delete")
    public Mono<ResponseEntity<RestResponse>> deleteProvince(
            @RequestHeader(CommonConstants.REQ_HEADER_APIKEY) String apiKey,
            @RequestBody DeleteProvinceRequest deleteProvinceRequest){
        ValidationResponse validationResponse = deleteProvinceService.execute(deleteProvinceRequest);
        return Mono.just(new ResponseEntity<>( new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, MessageCode.OK.getValue(), validationResponse.getResult()), HttpStatus.OK));
    }

}
