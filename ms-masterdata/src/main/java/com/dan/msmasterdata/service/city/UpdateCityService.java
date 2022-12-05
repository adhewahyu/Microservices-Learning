package com.dan.msmasterdata.service.city;

import com.alibaba.fastjson2.JSON;
import com.dan.msmasterdata.adaptor.rest.AddTaskAdaptor;
import com.dan.msmasterdata.model.request.city.UpdateCityRequest;
import com.dan.msmasterdata.model.request.province.UpdateProvinceRequest;
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
public class UpdateCityService implements BaseService<UpdateCityRequest, ValidationResponse> {

    private final AddTaskAdaptor addTaskAdaptor;
    private final ValidateCityService validateCityService;
    private final CommonUtility commonUtility;

    @Override
    public ValidationResponse execute(UpdateCityRequest input) {
        validateCityService.execute(Constants.VALIDATION_TYPE_UPDATE,
                input.getId(), input.getCityCode(), input.getCityName(),
                input.getIsActive(), input.getProvinceCode(), input.getUpdatedBy(), input.getUpdatedDate());
        AddTaskRequest addTaskRequest = commonUtility.generateDefaultAddTaskRequest(TaskAction.UPDATE.getValue(), input.getUpdatedBy(), input.getUpdatedDate());
        addTaskRequest.setModule(Constants.MODULE_CITY);
        addTaskRequest.setTaskAfter(JSON.toJSONString(input));
        addTaskAdaptor.execute(addTaskRequest);
        return ValidationResponse.builder().result(true).build();
    }

}
