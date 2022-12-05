package com.dan.msmasterdata.model.request.city;

import com.dan.msmasterdata.model.request.province.BaseProvinceRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddCityRequest extends BaseCityRequest {

    @ApiModelProperty(name = "Created Date", example = "Date as long milisecond(s)")
    private Long createdDate;

    @ApiModelProperty(name = "Created By", example = "User Admin / admin@admin.net")
    private String createdBy;

}
