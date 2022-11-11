package com.dan.mstask.model.request;

import com.dan.shared.model.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseTaskRequest extends BaseRequest {

    @ApiModelProperty(name = "module", example = "MY-MODULE")
    private String module;

    @ApiModelProperty(name = "taskAfter", example = "Task After")
    private String taskAfter;

}
