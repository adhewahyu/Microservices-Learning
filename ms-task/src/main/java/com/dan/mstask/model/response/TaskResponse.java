package com.dan.mstask.model.response;

import com.dan.shared.model.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse extends BaseResponse {

    private String id;
    private String module;
    private String action;
    private String taskBefore;
    private String taskAfter;
    private Integer status;
    private Long createdDate;
    private String createdBy;
    private Long approvedDate;
    private String approvedBy;
    private Long rejectedDate;
    private String rejectedBy;

}
