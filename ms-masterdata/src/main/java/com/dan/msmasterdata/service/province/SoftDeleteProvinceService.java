package com.dan.msmasterdata.service.province;

import com.dan.msmasterdata.repository.ProvinceRepository;
import com.dan.msmasterdata.utility.CommonUtility;
import com.dan.shared.model.request.FindByIdRequest;
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
public class SoftDeleteProvinceService implements BaseService<FindByIdRequest, ValidationResponse> {

    private final CommonUtility commonUtility;
    private final ProvinceRepository provinceRepository;

    @Override
    public ValidationResponse execute(FindByIdRequest input) {
        log.info("SoftDeleteProvinceService - Called");
        doValidateRequest(input);
        provinceRepository.findById(input.getId()).ifPresentOrElse(data ->{
            data.setIsDeleted(true);

        },()->{
            log.error("Data Province not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, CommonConstants.ERR_MSG_DATA_NOT_FOUND);
        });
        return null;
    }

    private void doValidateRequest(FindByIdRequest input){
        commonUtility.doValidateId(input.getId());
    }

}
