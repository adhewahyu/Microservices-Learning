package com.dan.msmasterdata.service.province;

import com.dan.msmasterdata.repository.ProvinceRepository;
import com.dan.msmasterdata.utility.CommonUtility;
import com.dan.msmasterdata.utility.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static com.dan.msmasterdata.utility.Constants.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ValidateProvinceService {

    private final CommonUtility commonUtility;
    private final ProvinceRepository provinceRepository;

    public void execute(String type, String id, String provinceCode, String provinceName, Boolean isActive, String submitBy, Long submitDate){
        switch (type){
            case VALIDATION_TYPE_ADD:
                doValidateAddProvince(provinceCode, provinceName, submitBy, submitDate);
                break;
            case VALIDATION_TYPE_UPDATE:
                doValidateUpdateProvince(id, provinceCode, provinceName, isActive, submitBy, submitDate);
                break;
            case VALIDATION_TYPE_DELETE:
                commonUtility.doValidateDeleteMasterData(id, submitBy, submitDate);
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ERR_MSG_UNKNOWN_TASK_TYPE);
        }
    }

    private void doValidateUpdateProvince(String id, String provinceCode, String provinceName, Boolean isActive, String submitBy, Long submitDate){
        commonUtility.doValidateId(id);
        doValidateBaseProvince(provinceCode, provinceName);
        if(provinceRepository.countProvinceByProvinceCode(provinceCode) > 0 || provinceRepository.countProvinceByProvinceName(provinceName) > 0){
            log.error("Duplicate province code / name");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Province code or name already exists");
        }
        commonUtility.doValidateUpdateMasterData(isActive, submitBy, submitDate);
    }

    private void doValidateAddProvince(String provinceCode, String provinceName, String submitBy, Long submitDate){
        doValidateBaseProvince(provinceCode, provinceName);
        if(provinceRepository.countProvinceByProvinceCode(provinceCode) > 0 || provinceRepository.countProvinceByProvinceName(provinceName) > 0){
            log.error("Duplicate province code / name");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Province code or name already exists");
        }
        if(StringUtils.isEmpty(submitBy)){
            log.error("Created By is required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Created By is required");
        }
        if(ObjectUtils.isEmpty(submitDate)){
            log.error("Created Date is required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Created Date is required");
        }
    }

    private void doValidateBaseProvince(String provinceCode, String provinceName){
        if(StringUtils.isEmpty(provinceCode)){
            log.error("Province Code is required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Province Code is required");
        }
        if(StringUtils.isEmpty(provinceName)){
            log.error("Province Name is required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Province Name is required");
        }
    }

}
