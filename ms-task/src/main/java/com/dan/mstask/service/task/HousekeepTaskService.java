package com.dan.mstask.service.task;

import com.dan.shared.model.request.BaseRequest;
import com.dan.shared.model.response.ValidationResponse;
import com.dan.shared.service.BaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HousekeepTaskService implements BaseService<BaseRequest, ValidationResponse> {

    @Value("${config.housekeep.interval.task}")
    private String intervalHousekeepTask;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public ValidationResponse execute(BaseRequest input) {
        log.info("HousekeepTaskService - Called");
        jdbcTemplate.execute("DELETE FROM TASKS WHERE STATUS IN (0,2) AND CREATED_DATE <  (CURRENT_DATE - interval '"+intervalHousekeepTask+"')");
        return ValidationResponse.builder().result(true).build();
    }
    
}
