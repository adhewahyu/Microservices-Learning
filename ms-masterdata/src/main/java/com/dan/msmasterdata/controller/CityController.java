package com.dan.msmasterdata.controller;

import com.dan.msmasterdata.model.request.city.AddCityRequest;
import com.dan.msmasterdata.model.request.city.DeleteCityRequest;
import com.dan.msmasterdata.model.request.city.UpdateCityRequest;
import com.dan.msmasterdata.service.city.AddCityService;
import com.dan.msmasterdata.service.city.DeleteCityService;
import com.dan.msmasterdata.service.city.UpdateCityService;
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

@Api(tags = "City APIs")
@RestController
@RequestMapping("/city")
@RequiredArgsConstructor
public class CityController extends BaseController {

    private final AddCityService addCityService;
    private final UpdateCityService updateCityService;
    private final DeleteCityService deleteCityService;

    @PostMapping("/v1/add")
    public Mono<ResponseEntity<RestResponse>> addCity(
            @RequestHeader(CommonConstants.REQ_HEADER_APIKEY) String apiKey,
            @RequestBody AddCityRequest addCityRequest){
        ValidationResponse validationResponse = addCityService.execute(addCityRequest);
        return Mono.just(new ResponseEntity<>( new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, MessageCode.OK.getValue(), validationResponse.getResult()), HttpStatus.OK));
    }

    @PutMapping("/v1/update")
    public Mono<ResponseEntity<RestResponse>> updateCity(
            @RequestHeader(CommonConstants.REQ_HEADER_APIKEY) String apiKey,
            @RequestBody UpdateCityRequest updateCityRequest){
        ValidationResponse validationResponse = updateCityService.execute(updateCityRequest);
        return Mono.just(new ResponseEntity<>( new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, MessageCode.OK.getValue(), validationResponse.getResult()), HttpStatus.OK));
    }

    @DeleteMapping("/v1/delete")
    public Mono<ResponseEntity<RestResponse>> deleteCity(
            @RequestHeader(CommonConstants.REQ_HEADER_APIKEY) String apiKey,
            @RequestBody DeleteCityRequest deleteCityRequest){
        ValidationResponse validationResponse = deleteCityService.execute(deleteCityRequest);
        return Mono.just(new ResponseEntity<>( new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, MessageCode.OK.getValue(), validationResponse.getResult()), HttpStatus.OK));
    }

}
