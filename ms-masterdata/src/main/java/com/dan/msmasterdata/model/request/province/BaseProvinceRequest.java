package com.dan.msmasterdata.model.request.province;

import com.dan.shared.model.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Setter
@Getter
public class BaseProvinceRequest extends BaseRequest {

    @ApiModelProperty(name = "Province Code", example = "JKT")
    private String provinceCode;

    @ApiModelProperty(name = "Province Name", example = "Jakarta")
    private String provinceName;

}
