package com.dan.msaudit.model.response;

import com.dan.shared.model.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuditResponse extends BaseResponse {

    private String id;
    private String createdBy;
    private String module;
    private String activity;
    private Long createdDate;
    
}
