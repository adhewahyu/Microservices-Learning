package com.dan.msmasterdata.model.request.city;

import com.dan.shared.model.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseCityRequest extends BaseRequest {

    @ApiModelProperty(name = "City Code", example = "JKT-PUS")
    private String cityCode;

    @ApiModelProperty(name = "City Name", example = "Jakarta Pusat")
    private String cityName;

    @ApiModelProperty(name = "Province Code", example = "JKT")
    private String provinceCode;

}
