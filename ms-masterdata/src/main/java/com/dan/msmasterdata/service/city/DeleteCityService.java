package com.dan.msmasterdata.service.city;

import com.alibaba.fastjson2.JSON;
import com.dan.msmasterdata.adaptor.rest.AddTaskAdaptor;
import com.dan.msmasterdata.model.request.city.DeleteCityRequest;
import com.dan.msmasterdata.model.request.province.DeleteProvinceRequest;
import com.dan.msmasterdata.model.request.task.AddTaskRequest;
import com.dan.msmasterdata.repository.CityRepository;
import com.dan.msmasterdata.repository.ProvinceRepository;
import com.dan.msmasterdata.utility.CommonUtility;
import com.dan.msmasterdata.utility.Constants;
import com.dan.shared.enums.TaskAction;
import com.dan.shared.model.response.ValidationResponse;
import com.dan.shared.service.BaseService;
import com.dan.shared.utility.CommonConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@Slf4j
@RequiredArgsConstructor
public class DeleteCityService implements BaseService<DeleteCityRequest, ValidationResponse> {

    private final CityRepository cityRepository;
    private final AddTaskAdaptor addTaskAdaptor;
    private final ValidateCityService validateCityService;
    private final CommonUtility commonUtility;

    @Override
    public ValidationResponse execute(DeleteCityRequest input) {
        log.info("SoftDeleteProvinceService - Called");
        validateCityService.execute(Constants.VALIDATION_TYPE_DELETE,
                input.getId(), null, null,
                null, null, input.getUpdatedBy(), input.getUpdatedDate());
        cityRepository.findById(input.getId()).ifPresentOrElse(data ->{
            AddTaskRequest addTaskRequest = commonUtility.generateDefaultAddTaskRequest(TaskAction.DELETE.getValue(), input.getUpdatedBy(), input.getUpdatedDate());
            addTaskRequest.setModule(Constants.MODULE_CITY);
            addTaskRequest.setTaskAfter(JSON.toJSONString(input));
            addTaskAdaptor.execute(addTaskRequest);
        },()->{
            log.error("Data Province not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, CommonConstants.ERR_MSG_DATA_NOT_FOUND);
        });
        return ValidationResponse.builder().result(true).build();
    }

}
