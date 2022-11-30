package com.dan.msmasterdata.service.province;

import com.alibaba.fastjson2.JSON;
import com.dan.msmasterdata.adaptor.rest.AddTaskAdaptor;
import com.dan.msmasterdata.model.request.province.DeleteProvinceRequest;
import com.dan.msmasterdata.model.request.task.AddTaskRequest;
import com.dan.msmasterdata.repository.ProvinceRepository;
import com.dan.msmasterdata.utility.CommonUtility;
import com.dan.msmasterdata.utility.Constants;
import com.dan.shared.enums.TaskAction;
import com.dan.shared.model.response.ValidationResponse;
import com.dan.shared.service.BaseService;
import com.dan.shared.utility.CommonConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@Slf4j
@RequiredArgsConstructor
public class DeleteProvinceService implements BaseService<DeleteProvinceRequest, ValidationResponse> {

    private final ProvinceRepository provinceRepository;
    private final AddTaskAdaptor addTaskAdaptor;
    private final ValidateProvinceService validateProvinceService;

    @Override
    public ValidationResponse execute(DeleteProvinceRequest input) {
        log.info("SoftDeleteProvinceService - Called");
        validateProvinceService.execute(Constants.VALIDATION_TYPE_DELETE,
                input.getId(), null, null,
                null, true, input.getUpdatedBy(), input.getUpdatedDate());
        provinceRepository.findById(input.getId()).ifPresentOrElse(data ->{
            AddTaskRequest addTaskRequest = AddTaskRequest.builder()
                    .action(TaskAction.DELETE.getValue())
                    .createdBy(input.getUpdatedBy())
                    .createdDate(input.getUpdatedDate())
                    .build();
            addTaskRequest.setModule(Constants.MODULE_PROVINCE);
            addTaskRequest.setTaskAfter(JSON.toJSONString(input));
            addTaskAdaptor.execute(addTaskRequest);
        },()->{
            log.error("Data Province not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, CommonConstants.ERR_MSG_DATA_NOT_FOUND);
        });
        return ValidationResponse.builder().result(true).build();
    }

}
