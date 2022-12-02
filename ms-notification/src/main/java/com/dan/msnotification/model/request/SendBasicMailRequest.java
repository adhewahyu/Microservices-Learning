package com.dan.msnotification.model.request;

import com.dan.shared.model.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class SendBasicMailRequest extends BaseRequest {

    @ApiModelProperty(example = "test@gmail.com")
    private String mailTo;

    @ApiModelProperty(example = "test")
    private String subject;

    @ApiModelProperty(example = "test sending email")
    private String message;

}
