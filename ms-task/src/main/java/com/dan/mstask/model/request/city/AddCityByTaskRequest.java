package com.dan.mstask.model.request.city;

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
public class AddCityByTaskRequest extends BaseRequest {

    @ApiModelProperty(name = "City Code", example = "JKT-PUS")
    private String cityCode;

    @ApiModelProperty(name = "City Name", example = "Jakarta Pusat")
    private String cityName;

    @ApiModelProperty(name = "Province Code", example = "JKT")
    private String provinceCode;

    @ApiModelProperty(name = "Created Date", example = "Date as long milisecond(s)")
    private Long createdDate;

    @ApiModelProperty(name = "Created By", example = "User Admin / admin@admin.net")
    private String createdBy;

}
