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
public class ChangePasswordRequest extends BaseRequest {

    @ApiModelProperty(example ="adhe@adhe.com", value="Email")
    private String email;

    @ApiModelProperty(example = "password", value = "Your Password")
    private String password;

}
