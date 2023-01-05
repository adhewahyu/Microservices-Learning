package com.dan.msmasterdata.utility;

import com.dan.msmasterdata.model.entity.City;
import com.dan.msmasterdata.model.entity.Province;
import com.dan.msmasterdata.model.request.task.AddTaskRequest;
import com.dan.shared.enums.TaskAction;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
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

    public void doValidateDeleteMasterData(String id, String submitBy, Long submitDate){
        this.doValidateId(id);
        if(StringUtils.isEmpty(submitBy)){
            log.error("Updated By is required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Updated By is required");
        }
        if(ObjectUtils.isEmpty(submitDate)){
            log.error("Updated Date is required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Updated Date is required");
        }
    }

    public void doValidateUpdateMasterData(Boolean isActive, String submitBy, Long submitDate){
        if(ObjectUtils.isEmpty(isActive)){
            log.error("Flag isActive is required");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Flag isActive is required");
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

    public AddTaskRequest generateDefaultAddTaskRequest(String action, String createdBy, Long createdDate){
        return AddTaskRequest.builder()
                .action(action)
                .createdBy(createdBy)
                .createdDate(createdDate)
                .build();
    }

    public void doResolveCommonCreateData(Object object){
        if(object instanceof Province){
            ((Province) object).setIsDeleted(false);
            ((Province) object).setIsActive(true);
        }
        if(object instanceof City){
            ((City) object).setIsDeleted(false);
            ((City) object).setIsActive(true);
        }
    }

}
