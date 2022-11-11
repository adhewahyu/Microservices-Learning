package com.dan.mstask.model.request.task;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitTaskRequest extends BaseTaskRequest {

    @ApiModelProperty(name = "Task ID", example = "1")
    private String id;

    @ApiModelProperty(name = "Task Status", example = "1 (Approve) / 2 (Reject)")
    private Integer status;

    @ApiModelProperty(name = "Submit Date", example = "Date as long milisecond(s)")
    private Long submitDate;

    @ApiModelProperty(name = "Submit By", example = "User Admin / admin@admin.net")
    private String submitBy;


}
