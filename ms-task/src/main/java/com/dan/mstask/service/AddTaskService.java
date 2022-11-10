package com.dan.mstask.service;

import com.dan.mstask.enums.TaskAction;
import com.dan.mstask.enums.TaskStatus;
import com.dan.mstask.model.entity.Task;
import com.dan.mstask.model.request.AddTaskRequest;
import com.dan.mstask.repository.TaskRepository;
import com.dan.mstask.utility.Constants;
import com.dan.shared.model.response.ValidationResponse;
import com.dan.shared.service.BaseService;
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
        return ValidationResponse.builder().result(true).build();
    }

}
