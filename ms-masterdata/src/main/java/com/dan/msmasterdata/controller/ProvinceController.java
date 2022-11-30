package com.dan.msmasterdata.controller;

import com.dan.msmasterdata.model.entity.Province;
import com.dan.msmasterdata.model.request.province.*;
import com.dan.msmasterdata.service.province.*;
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

@Api(tags = "Province APIs")
@RestController
@RequestMapping("/province")
@RequiredArgsConstructor
public class ProvinceController extends BaseController {

    private final AddProvinceService addProvinceService;
    private final AddProvinceByTaskService addProvinceByTaskService;
    private final UpdateProvinceService updateProvinceService;
    private final UpdateProvinceByTaskService updateProvinceByTaskService;
    private final DeleteProvinceService deleteProvinceService;
    private final DeleteProvinceByTaskService deleteProvinceByTaskService;
    private final SearchProvinceService searchProvinceService;

    @PostMapping("/v1/add")
    public Mono<ResponseEntity<RestResponse>> addProvince(
            @RequestHeader(CommonConstants.REQ_HEADER_APIKEY) String apiKey,
            @RequestBody AddProvinceRequest addProvinceRequest){
        ValidationResponse validationResponse = addProvinceService.execute(addProvinceRequest);
        return Mono.just(new ResponseEntity<>( new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, MessageCode.OK.getValue(), validationResponse.getResult()), HttpStatus.OK));
    }

    @PostMapping("/v1/add-province-by-task")
    public Mono<ResponseEntity<RestResponse>> addProvince(
            @RequestHeader(CommonConstants.REQ_HEADER_APIKEY) String apiKey,
            @RequestBody AddProvinceByTaskRequest addProvinceByTaskRequest){
        ValidationResponse validationResponse = addProvinceByTaskService.execute(addProvinceByTaskRequest);
        return Mono.just(new ResponseEntity<>( new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, MessageCode.OK.getValue(), validationResponse.getResult()), HttpStatus.OK));
    }

    @PostMapping("/v1/update")
    public Mono<ResponseEntity<RestResponse>> updateProvince(
            @RequestHeader(CommonConstants.REQ_HEADER_APIKEY) String apiKey,
            @RequestBody UpdateProvinceRequest updateProvinceRequest){
        ValidationResponse validationResponse = updateProvinceService.execute(updateProvinceRequest);
        return Mono.just(new ResponseEntity<>( new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, MessageCode.OK.getValue(), validationResponse.getResult()), HttpStatus.OK));
    }

    @PostMapping("/v1/update-province-by-task")
    public Mono<ResponseEntity<RestResponse>> addProvince(
            @RequestHeader(CommonConstants.REQ_HEADER_APIKEY) String apiKey,
            @RequestBody UpdateProvinceByTaskRequest updateProvinceByTaskRequest){
        ValidationResponse validationResponse = updateProvinceByTaskService.execute(updateProvinceByTaskRequest);
        return Mono.just(new ResponseEntity<>( new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, MessageCode.OK.getValue(), validationResponse.getResult()), HttpStatus.OK));
    }

    @PostMapping("/v1/delete")
    public Mono<ResponseEntity<RestResponse>> deleteProvince(
            @RequestHeader(CommonConstants.REQ_HEADER_APIKEY) String apiKey,
            @RequestBody DeleteProvinceRequest deleteProvinceRequest){
        ValidationResponse validationResponse = deleteProvinceService.execute(deleteProvinceRequest);
        return Mono.just(new ResponseEntity<>( new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, MessageCode.OK.getValue(), validationResponse.getResult()), HttpStatus.OK));
    }

    @PostMapping("/v1/delete-province-by-task")
    public Mono<ResponseEntity<RestResponse>> addProvince(
            @RequestHeader(CommonConstants.REQ_HEADER_APIKEY) String apiKey,
            @RequestBody DeleteProvinceByTaskRequest deleteProvinceByTaskRequest){
        ValidationResponse validationResponse = deleteProvinceByTaskService.execute(deleteProvinceByTaskRequest);
        return Mono.just(new ResponseEntity<>( new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, MessageCode.OK.getValue(), validationResponse.getResult()), HttpStatus.OK));
    }

    @GetMapping("/v1/search")
    public ResponseEntity<RestResponse> getTasks(
            @RequestHeader(value = CommonConstants.REQ_HEADER_APIKEY) String apiKey,
            SearchRequest searchRequest) {
        Specification<Province> specs = Specification.where((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get(Constants.FLD_DELETED), false));
            return criteriaBuilder.and(predicates.toArray(new Predicate[] {}));
        });
        return this.getPageResponse(searchProvinceService.execute(SpecificationRequest.builder()
                .pageable(getCommonPageable(searchRequest))
                .specification(specs).build()).getPage());
    }

}
