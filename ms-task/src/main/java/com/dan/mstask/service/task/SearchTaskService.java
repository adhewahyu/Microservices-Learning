package com.dan.mstask.service.task;

import com.alibaba.fastjson2.JSON;
import com.dan.mstask.model.entity.Task;
import com.dan.mstask.model.response.TaskResponse;
import com.dan.mstask.repository.TaskRepository;
import com.dan.shared.model.request.SpecificationRequest;
import com.dan.shared.model.response.PageResponse;
import com.dan.shared.service.BaseService;
import com.dan.shared.utility.SharedUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class SearchTaskService implements BaseService<SpecificationRequest, PageResponse> {

    private final TaskRepository taskRepository;
    private final SharedUtility sharedUtility;

    @Override
    public PageResponse execute(SpecificationRequest input) {
        log.info("SearchTaskService - Called");
        Page<Task> pageTask = this.taskRepository.findAll(input.getSpecification(), input.getPageable());
        return PageResponse.builder().page(pageTask.isEmpty() ? null : pageTask.map(data ->
                TaskResponse.builder()
                        .id(data.getId())
                        .module(data.getModule())
                        .action(data.getAction())
                        .taskBefore(data.getTaskBefore())
                        .taskAfter(data.getTaskAfter())
                        .status(data.getStatus())
                        .createdDate(sharedUtility.getDateOrReturnNull(data.getCreatedDate()))
                        .createdBy(data.getCreatedBy())
                        .approvedDate(sharedUtility.getDateOrReturnNull(data.getApprovedDate()))
                        .approvedBy(data.getApprovedBy())
                        .rejectedDate(sharedUtility.getDateOrReturnNull(data.getRejectedDate()))
                        .rejectedBy(data.getRejectedBy())
                        .build()
        )).build();
    }

}
