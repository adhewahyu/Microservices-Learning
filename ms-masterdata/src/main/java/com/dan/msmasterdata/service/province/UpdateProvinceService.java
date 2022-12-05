package com.dan.msmasterdata.service.province;

import com.alibaba.fastjson2.JSON;
import com.dan.msmasterdata.adaptor.rest.AddTaskAdaptor;
import com.dan.msmasterdata.model.request.province.UpdateProvinceRequest;
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
public class UpdateProvinceService implements BaseService<UpdateProvinceRequest, ValidationResponse> {

    private final AddTaskAdaptor addTaskAdaptor;
    private final ValidateProvinceService validateProvinceService;

    @Override
    public ValidationResponse execute(UpdateProvinceRequest input) {
        validateProvinceService.execute(Constants.VALIDATION_TYPE_UPDATE,
                input.getId(), input.getProvinceCode(), input.getProvinceName(),
                input.getIsActive(),  input.getUpdatedBy(), input.getUpdatedDate());
        AddTaskRequest addTaskRequest = AddTaskRequest.builder()
                .action(TaskAction.UPDATE.getValue())
                .createdBy(input.getUpdatedBy())
                .createdDate(input.getUpdatedDate())
                .build();
        addTaskRequest.setModule(Constants.MODULE_PROVINCE);
        addTaskRequest.setTaskAfter(JSON.toJSONString(input));
        addTaskAdaptor.execute(addTaskRequest);
        return ValidationResponse.builder().result(true).build();
    }

}
