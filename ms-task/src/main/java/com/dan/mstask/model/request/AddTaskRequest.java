package com.dan.mstask.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddTaskRequest extends BaseTaskRequest {

    @ApiModelProperty(name = "Created Date", example = "Date as long milisecond(s)")
    private Long createdDate;

    @ApiModelProperty(name = "Created By", example = "User Admin / admin@admin.net")
    private String createdBy;


}
