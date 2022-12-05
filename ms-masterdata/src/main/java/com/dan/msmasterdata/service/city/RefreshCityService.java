package com.dan.msmasterdata.service.city;

import com.alibaba.fastjson2.JSON;
import com.dan.msmasterdata.model.response.city.CityResponse;
import com.dan.msmasterdata.model.response.province.ProvinceResponse;
import com.dan.msmasterdata.model.transformer.CityResponseTransformer;
import com.dan.msmasterdata.repository.CityRepository;
import com.dan.msmasterdata.repository.ProvinceRepository;
import com.dan.msmasterdata.utility.Constants;
import com.dan.shared.model.request.BaseRequest;
import com.dan.shared.model.response.ValidationResponse;
import com.dan.shared.service.BaseService;
import com.dan.shared.utility.CacheUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RefreshCityService implements BaseService<BaseRequest, ValidationResponse> {

    private final CityRepository cityRepository;
    private final CacheUtility cacheUtility;
    private final CityResponseTransformer cityResponseTransformer;

    @Override
    public ValidationResponse execute(BaseRequest input) {
        log.info("RefreshCityService - Called");
        List<CityResponse> cityResponseList = cityRepository.findAllActiveCity()
                .stream()
                .map(cityResponseTransformer::transform)
                .collect(Collectors.toList());
        cacheUtility.set(Constants.RDS_MASTERDATA, Constants.RDS_MASTER_DATA_CITY, JSON.toJSONString(cityResponseList), null);
        return ValidationResponse.builder().result(true).build();
    }
}
