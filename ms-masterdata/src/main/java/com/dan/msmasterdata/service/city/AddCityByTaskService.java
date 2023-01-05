package com.dan.msmasterdata.service.city;

import com.dan.msmasterdata.model.entity.City;
import com.dan.msmasterdata.model.request.city.AddCityByTaskRequest;
import com.dan.msmasterdata.queue.publisher.city.PublishRefreshCityService;
import com.dan.msmasterdata.repository.CityRepository;
import com.dan.msmasterdata.utility.CommonUtility;
import com.dan.shared.model.request.BaseRequest;
import com.dan.shared.model.response.ValidationResponse;
import com.dan.shared.service.BaseService;
import com.dan.shared.utility.SharedUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AddCityByTaskService implements BaseService<AddCityByTaskRequest, ValidationResponse> {

    private final CityRepository cityRepository;
    private final SharedUtility sharedUtility;
    private final PublishRefreshCityService publishRefreshCityService;
    private final CommonUtility commonUtility;

    @Override
    public ValidationResponse execute(AddCityByTaskRequest input) {
        log.info("AddCityByTaskService - Called");
        City newCity = new City();
        newCity.setId(sharedUtility.getRandomUUID());
        newCity.setProvinceCode(input.getProvinceCode());
        newCity.setCityCode(input.getCityCode());
        newCity.setCityName(input.getCityName());
        commonUtility.doResolveCommonCreateData(newCity);
        newCity.setCreatedBy(input.getCreatedBy());
        newCity.setCreatedDate(new Date(input.getCreatedDate()));
        cityRepository.save(newCity);
        publishRefreshCityService.execute(new BaseRequest());
        return ValidationResponse.builder().result(true).build();
    }

}
