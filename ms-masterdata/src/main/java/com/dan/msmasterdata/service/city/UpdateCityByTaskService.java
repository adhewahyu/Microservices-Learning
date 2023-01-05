package com.dan.msmasterdata.service.city;

import com.dan.msmasterdata.model.request.city.UpdateCityByTaskRequest;
import com.dan.msmasterdata.queue.publisher.city.PublishRefreshCityService;
import com.dan.msmasterdata.repository.CityRepository;
import com.dan.shared.model.request.BaseRequest;
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

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class UpdateCityByTaskService implements BaseService<UpdateCityByTaskRequest, ValidationResponse> {

    private final CityRepository cityRepository;
    private final PublishRefreshCityService publishRefreshCityService;

    @Override
    public ValidationResponse execute(UpdateCityByTaskRequest input) {
        log.info("UpdateCityByTaskService - Called");
        cityRepository.findById(input.getId()).ifPresentOrElse(data ->{
            data.setProvinceCode(input.getProvinceCode());
            data.setCityCode(input.getCityCode());
            data.setCityName(input.getCityName());
            data.setIsActive(input.getIsActive());
            data.setUpdatedBy(input.getUpdatedBy());
            data.setUpdatedDate(new Date(input.getUpdatedDate()));
            cityRepository.save(data);
            publishRefreshCityService.execute(new BaseRequest());
        },()->{
            log.error("City with id = {} not found",input.getId());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, CommonConstants.ERR_MSG_DATA_NOT_FOUND);
        });
        return ValidationResponse.builder().result(true).build();
    }

}
