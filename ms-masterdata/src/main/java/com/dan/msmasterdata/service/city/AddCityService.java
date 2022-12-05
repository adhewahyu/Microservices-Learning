package com.dan.msmasterdata.service.city;

import com.alibaba.fastjson2.JSON;
import com.dan.msmasterdata.adaptor.rest.AddTaskAdaptor;
import com.dan.msmasterdata.model.request.city.AddCityRequest;
import com.dan.msmasterdata.model.request.province.AddProvinceRequest;
import com.dan.msmasterdata.model.request.task.AddTaskRequest;
import com.dan.msmasterdata.utility.CommonUtility;
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
public class AddCityService implements BaseService<AddCityRequest, ValidationResponse> {

    private final AddTaskAdaptor addTaskAdaptor;
    private final ValidateCityService validateCityService;
    private final CommonUtility commonUtility;

    @Override
    public ValidationResponse execute(AddCityRequest input) {
        validateCityService.execute(Constants.VALIDATION_TYPE_ADD,
                null, input.getCityCode(), input.getCityName(),
                null, input.getProvinceCode(), input.getCreatedBy(), input.getCreatedDate());
        AddTaskRequest addTaskRequest = commonUtility.generateDefaultAddTaskRequest(TaskAction.INSERT.getValue(), input.getCreatedBy(), input.getCreatedDate());
        addTaskRequest.setModule(Constants.MODULE_CITY);
        addTaskRequest.setTaskAfter(JSON.toJSONString(input));
        addTaskAdaptor.execute(addTaskRequest);
        return ValidationResponse.builder().result(true).build();
    }

}
