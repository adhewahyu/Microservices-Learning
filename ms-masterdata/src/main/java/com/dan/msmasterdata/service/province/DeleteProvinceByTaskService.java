package com.dan.msmasterdata.service.province;

import com.dan.msmasterdata.model.request.province.DeleteProvinceByTaskRequest;
import com.dan.msmasterdata.repository.ProvinceRepository;
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
public class DeleteProvinceByTaskService implements BaseService<DeleteProvinceByTaskRequest, ValidationResponse> {

    private final ProvinceRepository provinceRepository;

    @Override
    public ValidationResponse execute(DeleteProvinceByTaskRequest input) {
        log.info("DeleteProvinceByTaskService - Called");
        provinceRepository.findById(input.getId()).ifPresentOrElse(data->{
            data.setIsDeleted(true);
            data.setUpdatedBy(input.getUpdatedBy());
            data.setUpdatedDate(new Date(input.getUpdatedDate()));
            provinceRepository.save(data);
        },()->{
            log.error("Province with id = {} not found", input.getId());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, CommonConstants.ERR_MSG_DATA_NOT_FOUND);
        });
        return ValidationResponse.builder().result(true).build();
    }

}
