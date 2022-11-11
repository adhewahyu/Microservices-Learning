package com.dan.msmasterdata.model.transformer;

import com.dan.msmasterdata.model.entity.Province;
import com.dan.msmasterdata.model.response.province.ProvinceResponse;
import com.dan.shared.model.transformer.MessageTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProvinceResponseTransformer implements MessageTransformer<Province, ProvinceResponse> {

    @Override
    public ProvinceResponse transform(Province input) {
        return ProvinceResponse.builder()
                .id(input.getId())
                .provinceCode(input.getProvinceCode())
                .provinceName(input.getProvinceName())
                .isActive(input.getIsActive())
                .isDeleted(input.getIsDeleted())
                .build();
    }

}
