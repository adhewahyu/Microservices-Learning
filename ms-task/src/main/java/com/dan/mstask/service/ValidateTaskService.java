package com.dan.mstask.service;

import com.dan.mstask.enums.TaskAction;
import com.dan.mstask.enums.TaskStatus;
import com.dan.mstask.utility.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static com.dan.mstask.utility.Constants.TASK_TYPE_ADD;
import static com.dan.mstask.utility.Constants.TASK_TYPE_SUBMIT;

@Service
@Slf4j
public class ValidateTaskService {

    public void execute(String validationType, String action, String module, String task, String requester, Long requestDate, String id, Integer status){
        switch (validationType){
            case TASK_TYPE_ADD:
                doValidateAddTask(action, module, task, requester, requestDate);
                break;
            case TASK_TYPE_SUBMIT:
                doValidateSubmitTask(id, status, module, task, requester, requestDate);
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ERR_MSG_UNKNOWN_TASK_TYPE);
        }
    }

    private Boolean isValidTaskAction(String action){
        return Arrays.stream(TaskAction.values())
                .anyMatch(data -> data.getValue().equals(action));
    }

    private Boolean isValidTaskStatus(Integer status){
        return List.of(TaskStatus.APPROVED.getValue(), TaskStatus.REJECTED.getValue())
                .stream()
                .anyMatch(data -> data.compareTo(status) == 0);
    }

    private void doValidateAddTask(String action, String module, String taskAfter, String requester, Long requestDate){
        if(StringUtils.isEmpty(action)){
            log.error("Task Action is required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ERR_MSG_TASK_ACTION_REQUIRED);
        }
        if(!isValidTaskAction(action)){
            log.error("Invalid Task Action");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ERR_MSG_TASK_ACTION_INVALID);
        }
        doValidateBaseTask(module, taskAfter);
        if(StringUtils.isEmpty(requester)){
            log.error("Created By is required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ERR_MSG_CREATED_BY_REQUIRED);
        }
        if(ObjectUtils.isEmpty(requestDate)){
            log.error("Created Date is required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ERR_MSG_CREATED_DATE_REQUIRED);
        }
    }

    private void doValidateSubmitTask(String id, Integer status, String module, String task, String requester, Long requestDate){
        if(StringUtils.isEmpty(id)){
            log.error("Task Id is required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ERR_MSG_TASK_ID_REQUIRED);
        }
        if(ObjectUtils.isEmpty(status)){
            log.error("Task Status is required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ERR_MSG_TASK_STATUS_REQUIRED);
        }
        if(!isValidTaskStatus(status)){
            log.error("Invalid Task Status");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ERR_MSG_TASK_STATUS_INVALID);
        }
        doValidateBaseTask(module, task);
        if(StringUtils.isEmpty(requester)){
            log.error("Submit By is required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ERR_MSG_SUBMIT_BY_REQUIRED);
        }
        if(ObjectUtils.isEmpty(requestDate)){
            log.error("Submit Date is required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ERR_MSG_SUBMIT_DATE_REQUIRED);
        }
    }

    private void doValidateBaseTask(String module, String task){
        if(StringUtils.isEmpty(module)){
            log.error("Task Module is required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ERR_MSG_TASK_MODULE_REQUIRED);
        }
        if(StringUtils.isEmpty(task)){
            log.error("Task After is required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ERR_MSG_TASK_AFTER_REQUIRED);
        }
    }

}
