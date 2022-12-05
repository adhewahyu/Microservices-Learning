package com.dan.msmasterdata.service.city;

import com.dan.msmasterdata.repository.CityRepository;
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
public class ValidateCityService {

    private final CommonUtility commonUtility;
    private final CityRepository cityRepository;

    public void execute(String type, String id, String cityCode, String cityName, Boolean isActive, String provinceCode, String submitBy, Long submitDate){
        switch (type){
            case VALIDATION_TYPE_ADD:
                doValidateAddCity(cityCode, cityName, provinceCode, submitBy, submitDate);
                break;
            case VALIDATION_TYPE_UPDATE:
                doValidateUpdateCity(id, cityCode, cityName, isActive, provinceCode, submitBy, submitDate);
                break;
            case VALIDATION_TYPE_DELETE:
                commonUtility.doValidateDeleteMasterData(id, submitBy, submitDate);
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ERR_MSG_UNKNOWN_TASK_TYPE);
        }
    }

    private void doValidateUpdateCity(String id, String cityCode, String cityName, Boolean isActive, String provinceCode, String submitBy, Long submitDate){
        commonUtility.doValidateId(id);
        doValidateBaseCity(cityCode, cityName, provinceCode);
        if(cityRepository.countCityByCityCode(cityCode) > 0 || cityRepository.countCityByCityName(cityName) > 0){
            log.error("Duplicate City code / name");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "City code or name already exists");
        }
        commonUtility.doValidateUpdateMasterData(isActive, submitBy, submitDate);
    }

    private void doValidateAddCity(String cityCode, String cityName, String provinceCode, String submitBy, Long submitDate){
        doValidateBaseCity(cityCode, cityName, provinceCode);
        if(cityRepository.countCityByCityCode(cityCode) > 0 || cityRepository.countCityByCityName(cityName) > 0){
            log.error("Duplicate City code / name");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Province City or name already exists");
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

    private void doValidateBaseCity(String cityCode, String cityName, String provinceCode){
        if(StringUtils.isEmpty(cityCode)){
            log.error("City Code is required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "City Code is required");
        }
        if(StringUtils.isEmpty(cityName)){
            log.error("City Name is required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "City Name is required");
        }
        if(StringUtils.isEmpty(provinceCode)){
            log.error("Province Code is required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Province Code is required");
        }
    }

}
