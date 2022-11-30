package com.dan.mstask.controller;

import com.dan.mstask.model.entity.Task;
import com.dan.mstask.model.request.task.AddTaskRequest;
import com.dan.mstask.model.request.task.SubmitTaskRequest;
import com.dan.mstask.service.task.AddTaskService;
import com.dan.mstask.service.task.SearchTaskService;
import com.dan.mstask.service.task.SubmitTaskService;
import com.dan.mstask.utility.Constants;
import com.dan.shared.controller.BaseController;
import com.dan.shared.enums.MessageCode;
import com.dan.shared.enums.TaskStatus;
import com.dan.shared.model.request.SearchRequest;
import com.dan.shared.model.request.SpecificationRequest;
import com.dan.shared.model.response.RestResponse;
import com.dan.shared.model.response.ValidationResponse;
import com.dan.shared.utility.CommonConstants;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "Microservice Task APIs")
@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController extends BaseController {

    private final AddTaskService addTaskService;
    private final SubmitTaskService submitTaskService;
    private final SearchTaskService searchTaskService;

    @PostMapping("/v1/add")
    public Mono<ResponseEntity<RestResponse>> doAddTask(
            @RequestHeader(value = CommonConstants.REQ_HEADER_APIKEY) String apiKey,
            @RequestBody AddTaskRequest addTaskRequest){
        ValidationResponse validationResponse = addTaskService.execute(addTaskRequest);
        return Mono.just(
                new ResponseEntity<>(
                        new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, MessageCode.OK.getValue(), validationResponse.getResult())
                        , HttpStatus.OK));
    }

    @PostMapping("/v1/submit")
    public Mono<ResponseEntity<RestResponse>> doSubmitTask(
            @RequestHeader(value = CommonConstants.REQ_HEADER_APIKEY) String apiKey,
            @RequestBody SubmitTaskRequest submitTaskRequest){
        ValidationResponse validationResponse = submitTaskService.execute(submitTaskRequest);
        return Mono.just(
                new ResponseEntity<>(
                        new RestResponse(null, CommonConstants.SUCCESS_MSG_DATA_SUBMITTED, MessageCode.OK.getValue(), validationResponse.getResult())
                        , HttpStatus.OK));
    }

    @GetMapping("/v1/search")
    public ResponseEntity<RestResponse> getTasks(
            @RequestHeader(value = CommonConstants.REQ_HEADER_APIKEY) String apiKey,
            SearchRequest searchRequest) {
        Specification<Task> specs = Specification.where((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get(Constants.FLD_STATUS), TaskStatus.NEW.getValue()));
            return criteriaBuilder.and(predicates.toArray(new Predicate[] {}));
        });
        return this.getPageResponse(searchTaskService.execute(SpecificationRequest.builder()
                .pageable(getCommonPageable(searchRequest))
                .specification(specs).build()).getPage());
    }

}
