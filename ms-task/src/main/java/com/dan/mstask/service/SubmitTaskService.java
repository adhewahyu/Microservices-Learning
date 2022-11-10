package com.dan.mstask.service;

import com.dan.mstask.enums.TaskStatus;
import com.dan.mstask.model.request.SubmitTaskRequest;
import com.dan.mstask.repository.TaskRepository;
import com.dan.mstask.utility.Constants;
import com.dan.shared.model.response.ValidationResponse;
import com.dan.shared.service.BaseService;
import com.dan.shared.utility.CommonConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubmitTaskService implements BaseService<SubmitTaskRequest, ValidationResponse> {

    private final TaskRepository taskRepository;
    private final ValidateTaskService validateTaskService;

    @Override
    public ValidationResponse execute(SubmitTaskRequest input) {
        validateTaskService.execute(Constants.TASK_TYPE_SUBMIT, input.getAction(), input.getModule(), input.getTaskAfter(), input.getSubmitBy(), input.getSubmitDate(), input.getId(), input.getStatus());
        taskRepository.findByIdAndStatus(input.getId(), TaskStatus.NEW.getValue()).ifPresentOrElse(data ->{
            data.setTaskBefore(data.getTaskAfter());
            data.setTaskAfter(input.getTaskAfter());
            if(input.getStatus().compareTo(TaskStatus.APPROVED.getValue()) == 0){
                log.info("Set task with id = {} as APPROVED", input.getId());
                data.setStatus(TaskStatus.APPROVED.getValue());
                data.setApprovedBy(input.getSubmitBy());
                data.setApprovedDate(new Date(input.getSubmitDate()));
            }else {
                log.info("Set task with id = {} as REJECTED", input.getId());
                data.setStatus(TaskStatus.REJECTED.getValue());
                data.setRejectedBy(input.getSubmitBy());
                data.setRejectedDate(new Date(input.getSubmitDate()));
            }
            taskRepository.save(data);
        },()->{
            log.error("Task with id = {} is not found", input.getId());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, CommonConstants.ERR_MSG_DATA_NOT_FOUND);
        });
        return ValidationResponse.builder().result(true).build();
    }

}
