package com.dan.mstask.service.task;

import com.dan.mstask.enums.TaskAction;
import com.dan.mstask.enums.TaskStatus;
import com.dan.mstask.model.entity.Task;
import com.dan.mstask.model.request.audit.AddAuditRequest;
import com.dan.mstask.model.request.task.AddTaskRequest;
import com.dan.mstask.repository.TaskRepository;
import com.dan.mstask.service.audit.AddAuditAsyncService;
import com.dan.mstask.utility.Constants;
import com.dan.shared.model.response.ValidationResponse;
import com.dan.shared.service.BaseService;
import com.dan.shared.utility.CommonConstants;
import com.dan.shared.utility.SharedUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddTaskService implements BaseService<AddTaskRequest, ValidationResponse> {

    private final TaskRepository taskRepository;
    private final SharedUtility sharedUtility;
    private final ValidateTaskService validateTaskService;
    private final AddAuditAsyncService addAuditAsyncService;

    @Override
    public ValidationResponse execute(AddTaskRequest input) {
        validateTaskService.execute(Constants.TASK_TYPE_ADD, input.getAction(), input.getModule(), input.getTaskAfter(), input.getCreatedBy(), input.getCreatedDate(), null, null);
        Task newTask = new Task();
        newTask.setId(sharedUtility.getRandomUUID());
        newTask.setModule(input.getModule());
        newTask.setAction(input.getAction());
        newTask.setTaskBefore(null);
        newTask.setTaskAfter(input.getTaskAfter());
        newTask.setStatus(TaskStatus.NEW.getValue());
        newTask.setCreatedBy(input.getCreatedBy());
        newTask.setCreatedDate(new Date(input.getCreatedDate()));
        taskRepository.save(newTask);
        addAuditAsyncService.execute(AddAuditRequest.builder()
                .module(input.getModule())
                .activity(resolveActivity(input.getAction(), input.getModule()))
                .createdBy(input.getCreatedBy())
                .createdDate(input.getCreatedDate())
                .build());
        return ValidationResponse.builder().result(true).build();
    }

    private String resolveActivity(String action, String module){
        return "Add New Task" +
                CommonConstants.COMMON_SEPARATOR +
                "Action : " + TaskAction.valueOf(action).getMsg() +
                CommonConstants.COMMON_SEPARATOR +
                "Module : " + module;
    }

}
