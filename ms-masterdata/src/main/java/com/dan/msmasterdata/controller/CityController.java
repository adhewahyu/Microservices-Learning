package com.dan.msmasterdata.controller;

import com.dan.msmasterdata.model.entity.City;
import com.dan.msmasterdata.model.entity.Province;
import com.dan.msmasterdata.model.request.city.*;
import com.dan.msmasterdata.model.request.province.AddProvinceByTaskRequest;
import com.dan.msmasterdata.model.request.province.UpdateProvinceByTaskRequest;
import com.dan.msmasterdata.service.city.*;
import com.dan.msmasterdata.utility.Constants;
import com.dan.shared.controller.BaseController;
import com.dan.shared.enums.MessageCode;
import com.dan.shared.model.request.SearchRequest;
import com.dan.shared.model.request.SpecificationRequest;
import com.dan.shared.model.response.RestResponse;
import com.dan.shared.model.response.ValidationResponse;
import com.dan.shared.utility.CommonConstants;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "City APIs")
@RestController
@RequestMapping("/city")
@RequiredArgsConstructor
public class CityController extends BaseController {

    private final AddCityService addCityService;
    private final AddCityByTaskService addCityByTaskService;
    private final UpdateCityService updateCityService;
    private final UpdateCityByTaskService updateCityByTaskService;
    private final DeleteCityService deleteCityService;
    private final DeleteCityByTaskService deleteCityByTaskService;
    private final SearchCityService searchCityService;


    @PostMapping("/v1/add")
    public Mono<ResponseEntity<RestResponse>> addCity(
            @RequestHeader(CommonConstants.REQ_HEADER_APIKEY) String apiKey,
            @RequestBody AddCityRequest addCityRequest){
        ValidationResponse validationResponse = addCityService.execute(addCityRequest);
        return Mono.just(new ResponseEntity<>( new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, MessageCode.OK.getValue(), validationResponse.getResult()), HttpStatus.OK));
    }

    @PostMapping("/v1/add-by-task")
    public Mono<ResponseEntity<RestResponse>> addCityByTask(
            @RequestHeader(CommonConstants.REQ_HEADER_APIKEY) String apiKey,
            @RequestBody AddCityByTaskRequest addCityByTaskRequest){
        ValidationResponse validationResponse = addCityByTaskService.execute(addCityByTaskRequest);
        return Mono.just(new ResponseEntity<>( new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, MessageCode.OK.getValue(), validationResponse.getResult()), HttpStatus.OK));
    }

    @PutMapping("/v1/update")
    public Mono<ResponseEntity<RestResponse>> updateCity(
            @RequestHeader(CommonConstants.REQ_HEADER_APIKEY) String apiKey,
            @RequestBody UpdateCityRequest updateCityRequest){
        ValidationResponse validationResponse = updateCityService.execute(updateCityRequest);
        return Mono.just(new ResponseEntity<>( new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, MessageCode.OK.getValue(), validationResponse.getResult()), HttpStatus.OK));
    }

    @PostMapping("/v1/update-by-task")
    public Mono<ResponseEntity<RestResponse>> updateCityByTask(
            @RequestHeader(CommonConstants.REQ_HEADER_APIKEY) String apiKey,
            @RequestBody UpdateCityByTaskRequest updateCityByTaskRequest){
        ValidationResponse validationResponse = updateCityByTaskService.execute(updateCityByTaskRequest);
        return Mono.just(new ResponseEntity<>( new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, MessageCode.OK.getValue(), validationResponse.getResult()), HttpStatus.OK));
    }

    @DeleteMapping("/v1/delete")
    public Mono<ResponseEntity<RestResponse>> deleteCity(
            @RequestHeader(CommonConstants.REQ_HEADER_APIKEY) String apiKey,
            @RequestBody DeleteCityRequest deleteCityRequest){
        ValidationResponse validationResponse = deleteCityService.execute(deleteCityRequest);
        return Mono.just(new ResponseEntity<>( new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, MessageCode.OK.getValue(), validationResponse.getResult()), HttpStatus.OK));
    }

    @PostMapping("/v1/delete-by-task")
    public Mono<ResponseEntity<RestResponse>> deleteCityByTask(
            @RequestHeader(CommonConstants.REQ_HEADER_APIKEY) String apiKey,
            @RequestBody DeleteCityByTaskRequest deleteCityByTaskRequest){
        ValidationResponse validationResponse = deleteCityByTaskService.execute(deleteCityByTaskRequest);
        return Mono.just(new ResponseEntity<>( new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, MessageCode.OK.getValue(), validationResponse.getResult()), HttpStatus.OK));
    }

    @GetMapping("/v1/search")
    public ResponseEntity<RestResponse> searchProvince(
            @RequestHeader(value = CommonConstants.REQ_HEADER_APIKEY) String apiKey,
            SearchRequest searchRequest) {
        Specification<City> specs = Specification.where((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get(Constants.FLD_DELETED), false));
            return criteriaBuilder.and(predicates.toArray(new Predicate[] {}));
        });
        return this.getPageResponse(searchCityService.execute(SpecificationRequest.builder()
                .pageable(getCommonPageable(searchRequest))
                .specification(specs)
                .build())
                .getPage());
    }

}
