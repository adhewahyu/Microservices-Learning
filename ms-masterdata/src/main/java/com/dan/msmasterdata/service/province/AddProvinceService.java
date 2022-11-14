package com.dan.msmasterdata.service.province;

import com.alibaba.fastjson2.JSON;
import com.dan.msmasterdata.adaptor.rest.AddTaskAdaptor;
import com.dan.msmasterdata.model.request.province.AddProvinceRequest;
import com.dan.msmasterdata.model.request.task.AddTaskRequest;
import com.dan.msmasterdata.utility.Constants;
import com.dan.shared.enums.TaskAction;
import com.dan.shared.model.response.ValidationResponse;
import com.dan.shared.service.BaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddProvinceService implements BaseService<AddProvinceRequest, ValidationResponse> {

    private final AddTaskAdaptor addTaskAdaptor;
    private final ValidateProvinceService validateProvinceService;

    @Override
    public ValidationResponse execute(AddProvinceRequest input) {
        validateProvinceService.execute(Constants.VALIDATION_TYPE_ADD,
                null, input.getProvinceCode(), input.getProvinceName(),
                null, null, input.getCreatedBy(), input.getCreatedDate());
        AddTaskRequest addTaskRequest = AddTaskRequest.builder()
                .action(TaskAction.INSERT.getValue())
                .createdBy(input.getCreatedBy())
                .createdDate(input.getCreatedDate())
                .build();
        addTaskRequest.setModule(Constants.MODULE_PROVINCE);
        addTaskRequest.setTaskAfter(JSON.toJSONString(input));
        addTaskAdaptor.execute(addTaskRequest);
        return ValidationResponse.builder().result(true).build();
    }

}
