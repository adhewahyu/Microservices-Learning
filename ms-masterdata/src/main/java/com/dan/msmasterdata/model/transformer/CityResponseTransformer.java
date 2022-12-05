package com.dan.msmasterdata.model.transformer;

import com.dan.msmasterdata.model.entity.City;
import com.dan.msmasterdata.model.entity.Province;
import com.dan.msmasterdata.model.response.city.CityResponse;
import com.dan.msmasterdata.model.response.province.ProvinceResponse;
import com.dan.shared.model.transformer.MessageTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CityResponseTransformer implements MessageTransformer<City, CityResponse> {

    @Override
    public CityResponse transform(City input) {
        return CityResponse.builder()
                .id(input.getId())
                .provinceCode(input.getProvinceCode())
                .cityCode(input.getCityCode())
                .cityName(input.getCityName())
                .isActive(input.getIsActive())
                .isDeleted(input.getIsDeleted())
                .build();
    }

}
