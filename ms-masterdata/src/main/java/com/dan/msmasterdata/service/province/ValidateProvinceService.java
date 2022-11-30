package com.dan.msmasterdata.service.province;

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

    public void execute(String type, String id, String provinceCode, String provinceName, Boolean isActive, Boolean isDeleted, String submitBy, Long submitDate){
        switch (type){
            case VALIDATION_TYPE_ADD:
                doValidateAddProvince(provinceCode, provinceName, submitBy, submitDate);
                break;
            case VALIDATION_TYPE_UPDATE:
                doValidateUpdateProvince(id, provinceCode, provinceName, isActive, isDeleted, submitBy, submitDate);
                break;
            case VALIDATION_TYPE_DELETE:
                doValidateDeleteProvince(id, submitBy, submitDate);
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ERR_MSG_UNKNOWN_TASK_TYPE);
        }
    }

    private void doValidateUpdateProvince(String id, String provinceCode, String provinceName, Boolean isActive, Boolean isDeleted, String submitBy, Long submitDate){
        commonUtility.doValidateId(id);
        doValidateBaseProvince(provinceCode, provinceName);
        if(ObjectUtils.isEmpty(isActive)){
            log.error("Flag isActive is required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Flag isActive is required");
        }
        if(ObjectUtils.isEmpty(isDeleted)){
            log.error("Flag isDeleted is required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Flag isDeleted is required");
        }
        if(StringUtils.isEmpty(submitBy)){
            log.error("Updated By is required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Updated By is required");
        }
        if(ObjectUtils.isEmpty(submitDate)){
            log.error("Updated Date is required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Updated Date is required");
        }
    }

    private void doValidateAddProvince(String provinceCode, String provinceName, String submitBy, Long submitDate){
        doValidateBaseProvince(provinceCode, provinceName);
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

    private void doValidateDeleteProvince(String id, String submitBy, Long submitDate){
        commonUtility.doValidateId(id);
        if(StringUtils.isEmpty(submitBy)){
            log.error("Updated By is required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Updated By is required");
        }
        if(ObjectUtils.isEmpty(submitDate)){
            log.error("Updated Date is required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Updated Date is required");
        }
    }

}