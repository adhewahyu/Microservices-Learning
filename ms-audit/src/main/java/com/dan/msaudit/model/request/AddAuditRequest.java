package com.dan.msaudit.model.request;

import com.dan.shared.model.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddAuditRequest extends BaseRequest{

    @ApiModelProperty(name = "module", example = "MY-MODULE")
    private String module;

    @ApiModelProperty(name = "activity", example = "Approve from MY-MODULE")
    private String activity;

    @ApiModelProperty(name = "Created Date", example = "Date as long milisecond(s)")
    private Long createdDate;

    @ApiModelProperty(name = "Created By", example = "User Admin / admin@admin.net")
    private String createdBy;



}
