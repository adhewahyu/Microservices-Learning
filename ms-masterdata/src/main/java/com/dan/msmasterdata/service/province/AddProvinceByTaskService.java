package com.dan.msmasterdata.service.province;

import com.dan.msmasterdata.model.entity.Province;
import com.dan.msmasterdata.model.request.province.AddProvinceByTaskRequest;
import com.dan.msmasterdata.queue.publisher.province.PublishProvinceService;
import com.dan.msmasterdata.repository.ProvinceRepository;
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
public class AddProvinceByTaskService implements BaseService<AddProvinceByTaskRequest, ValidationResponse> {

    private final ProvinceRepository provinceRepository;
    private final SharedUtility sharedUtility;
    private final PublishProvinceService publishProvinceService;

    @Override
    public ValidationResponse execute(AddProvinceByTaskRequest input) {
        log.info("AddProvinceByTaskService - Called");
        Province newProvince = new Province();
        newProvince.setId(sharedUtility.getRandomUUID());
        newProvince.setProvinceCode(input.getProvinceCode());
        newProvince.setProvinceName(input.getProvinceName());
        newProvince.setIsDeleted(false);
        newProvince.setIsActive(true);
        newProvince.setCreatedBy(input.getCreatedBy());
        newProvince.setCreatedDate(new Date(input.getCreatedDate()));
        provinceRepository.save(newProvince);
        publishProvinceService.execute(new BaseRequest());
        return ValidationResponse.builder().result(true).build();
    }

}
