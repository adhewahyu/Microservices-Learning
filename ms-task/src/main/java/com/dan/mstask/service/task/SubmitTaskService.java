package com.dan.mstask.service.task;

import com.alibaba.fastjson2.JSON;
import com.dan.mstask.adaptor.rest.city.AddCityByTaskAdaptor;
import com.dan.mstask.adaptor.rest.city.DeleteCityByTaskAdaptor;
import com.dan.mstask.adaptor.rest.city.UpdateCityByTaskAdaptor;
import com.dan.mstask.adaptor.rest.province.AddProvinceByTaskAdaptor;
import com.dan.mstask.adaptor.rest.province.DeleteProvinceByTaskAdaptor;
import com.dan.mstask.adaptor.rest.province.UpdateProvinceByTaskAdaptor;
import com.dan.mstask.model.entity.Task;
import com.dan.mstask.model.request.audit.AddAuditRequest;
import com.dan.mstask.model.request.city.AddCityByTaskRequest;
import com.dan.mstask.model.request.city.DeleteCityByTaskRequest;
import com.dan.mstask.model.request.city.UpdateCityByTaskRequest;
import com.dan.mstask.model.request.province.AddProvinceByTaskRequest;
import com.dan.mstask.model.request.province.DeleteProvinceByTaskRequest;
import com.dan.mstask.model.request.province.UpdateProvinceByTaskRequest;
import com.dan.mstask.model.request.task.SubmitTaskRequest;
import com.dan.mstask.repository.TaskRepository;
import com.dan.mstask.service.audit.AddAuditAsyncService;
import com.dan.mstask.utility.Constants;
import com.dan.shared.enums.TaskAction;
import com.dan.shared.enums.TaskStatus;
import com.dan.shared.model.response.ValidationResponse;
import com.dan.shared.service.BaseService;
import com.dan.shared.utility.CommonConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

import static com.dan.mstask.utility.Constants.MODULE_CITY;
import static com.dan.mstask.utility.Constants.MODULE_PROVINCE;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class SubmitTaskService implements BaseService<SubmitTaskRequest, ValidationResponse> {

    private final TaskRepository taskRepository;
    private final ValidateTaskService validateTaskService;
    private final AddProvinceByTaskAdaptor addProvinceByTaskAdaptor;
    private final UpdateProvinceByTaskAdaptor updateProvinceByTaskAdaptor;
    private final DeleteProvinceByTaskAdaptor deleteProvinceByTaskAdaptor;
    private final AddAuditAsyncService addAuditAsyncService;
    private final AddCityByTaskAdaptor addCityByTaskAdaptor;
    private final UpdateCityByTaskAdaptor updateCityByTaskAdaptor;
    private final DeleteCityByTaskAdaptor deleteCityByTaskAdaptor;

    @Override
    public ValidationResponse execute(SubmitTaskRequest input) {
        validateTaskService.execute(Constants.TASK_TYPE_SUBMIT, null, input.getModule(), input.getTaskAfter(), input.getSubmitBy(), input.getSubmitDate(), input.getId(), input.getStatus());
        taskRepository.findByIdAndStatus(input.getId(), TaskStatus.NEW.getValue()).ifPresentOrElse(data ->{
            doValidateTaskModule(input.getId(), input.getModule(), data.getModule());
            data.setTaskBefore(data.getTaskAfter());
            data.setTaskAfter(input.getTaskAfter());
            if(input.getStatus().compareTo(TaskStatus.APPROVED.getValue()) == 0){
                log.info("Set task with id = {} as APPROVED", input.getId());
                data.setStatus(TaskStatus.APPROVED.getValue());
                data.setApprovedBy(input.getSubmitBy());
                data.setApprovedDate(new Date(input.getSubmitDate()));
                this.doCallSubmitApiByTaskModule(data);
                this.doCallAudit(input, TaskStatus.APPROVED);
            }else {
                log.info("Set task with id = {} as REJECTED", input.getId());
                data.setStatus(TaskStatus.REJECTED.getValue());
                data.setRejectedBy(input.getSubmitBy());
                data.setRejectedDate(new Date(input.getSubmitDate()));
                this.doCallAudit(input, TaskStatus.REJECTED);
            }
            taskRepository.save(data);
        },()->{
            log.error("Task with id = {} is not found", input.getId());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, CommonConstants.ERR_MSG_DATA_NOT_FOUND);
        });
        return ValidationResponse.builder().result(true).build();
    }

    private void doValidateTaskModule(String inputId, String inputTaskModule, String sourceTaskModule){
        if(!inputTaskModule.equals(sourceTaskModule)){
            log.error("Invalid Task Module for task id = {}", inputId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ERR_MSG_TASK_MODULE_INVALID);
        }
    }

    private void doCallSubmitApiByTaskModule(Task approvedTask){
        switch (approvedTask.getModule()){
            case MODULE_PROVINCE:
                this.doSubmitProvinceAdaptor(approvedTask);
                break;
            case MODULE_CITY:
                this.doSubmitCityAdaptor(approvedTask);
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ERR_MSG_TASK_MODULE_INVALID);
        }
    }

    private void doSubmitProvinceAdaptor(Task approvedTask){
        if(approvedTask.getAction().equals(TaskAction.INSERT.getValue())){
            addProvinceByTaskAdaptor.execute(JSON.parseObject(approvedTask.getTaskAfter(), AddProvinceByTaskRequest.class));
        }else if(approvedTask.getAction().equals(TaskAction.UPDATE.getValue())){
            updateProvinceByTaskAdaptor.execute(JSON.parseObject(approvedTask.getTaskAfter(), UpdateProvinceByTaskRequest.class));
        }else if(approvedTask.getAction().equals(TaskAction.DELETE.getValue())){
            deleteProvinceByTaskAdaptor.execute(JSON.parseObject(approvedTask.getTaskAfter(), DeleteProvinceByTaskRequest.class));
        }
    }

    private void doSubmitCityAdaptor(Task approvedTask){
        if(approvedTask.getAction().equals(TaskAction.INSERT.getValue())){
            addCityByTaskAdaptor.execute(JSON.parseObject(approvedTask.getTaskAfter(), AddCityByTaskRequest.class));
        }else if(approvedTask.getAction().equals(TaskAction.UPDATE.getValue())){
            updateCityByTaskAdaptor.execute(JSON.parseObject(approvedTask.getTaskAfter(), UpdateCityByTaskRequest.class));
        }else if(approvedTask.getAction().equals(TaskAction.DELETE.getValue())){
            deleteCityByTaskAdaptor.execute(JSON.parseObject(approvedTask.getTaskAfter(), DeleteCityByTaskRequest.class));
        }
    }

    private void doCallAudit(SubmitTaskRequest input, TaskStatus taskStatus){
        addAuditAsyncService.execute(AddAuditRequest.builder()
                .module(input.getModule())
                .activity(resolveActivity(taskStatus, input.getModule()))
                .createdBy(input.getSubmitBy())
                .createdDate(input.getSubmitDate())
                .build());
    }

    private String resolveActivity(TaskStatus taskStatus, String module){
        return "Submit Task" +
                CommonConstants.COMMON_SEPARATOR +
                "Action : " + taskStatus.getMsg() +
                CommonConstants.COMMON_SEPARATOR +
                "Module : " + module;
    }



}
