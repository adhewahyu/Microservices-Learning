package com.dan.mstask.controller;

import com.dan.mstask.model.request.task.AddTaskRequest;
import com.dan.mstask.model.request.task.SubmitTaskRequest;
import com.dan.mstask.service.task.AddTaskService;
import com.dan.mstask.service.task.SubmitTaskService;
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

@Api(tags = "Microservice Task APIs")
@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController extends BaseController {

    private final AddTaskService addTaskService;
    private final SubmitTaskService submitTaskService;

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

}
