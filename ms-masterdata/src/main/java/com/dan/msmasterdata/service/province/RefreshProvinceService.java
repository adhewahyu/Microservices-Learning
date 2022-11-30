package com.dan.msmasterdata.service.province;

import com.alibaba.fastjson2.JSON;
import com.dan.msmasterdata.model.response.province.ProvinceListResponse;
import com.dan.msmasterdata.model.response.province.ProvinceResponse;
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
public class RefreshProvinceService implements BaseService<BaseRequest, ValidationResponse> {

    private final ProvinceRepository provinceRepository;
    private final CacheUtility cacheUtility;

    @Override
    public ValidationResponse execute(BaseRequest input) {
        List<ProvinceResponse> provinceResponseList = provinceRepository.findAllActiveProvince()
                .stream()
                .map(data -> ProvinceResponse.builder()
                        .id(data.getId())
                        .provinceCode(data.getProvinceCode())
                        .provinceName(data.getProvinceName())
                        .isActive(data.getIsActive())
                        .isDeleted(data.getIsDeleted())
                        .build()).collect(Collectors.toList());
        cacheUtility.set(Constants.RDS_MASTERDATA, Constants.RDS_MASTER_DATA_PROVINSI, JSON.toJSONString(provinceResponseList), null);
        return ValidationResponse.builder().result(true).build();
    }
}
