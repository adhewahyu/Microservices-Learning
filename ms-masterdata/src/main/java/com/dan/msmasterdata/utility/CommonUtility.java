package com.dan.msmasterdata.utility;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@Slf4j
public class CommonUtility {

    public void doValidateId(String id){
        if(StringUtils.isEmpty(id)){
            log.error("Id is empty");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.ERR_MSG_ID_REQUIRED);
        }
    }

}
