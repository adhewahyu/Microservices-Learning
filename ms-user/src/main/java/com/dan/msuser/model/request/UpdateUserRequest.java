package com.dan.msuser.model.request;

import com.dan.shared.model.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest extends BaseRequest {

    @ApiModelProperty(example ="adhe@adhe.com", value="Email")
    private String email;

    @ApiModelProperty(example ="Adhe", value="Full Name")
    private String fullname;

    @ApiModelProperty(example ="1234-1234-1234", value="Role Id")
    private String roleId;

    @ApiModelProperty(example ="true", value="Set user activation status")
    private Boolean isActive;

}
