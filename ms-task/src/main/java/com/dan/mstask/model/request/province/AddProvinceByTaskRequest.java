package com.dan.mstask.model.request.province;

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
public class AddProvinceByTaskRequest extends BaseRequest {

    @ApiModelProperty(name = "Province Code", example = "JKT")
    private String provinceCode;

    @ApiModelProperty(name = "Province Name", example = "Jakarta")
    private String provinceName;

    @ApiModelProperty(name = "Created Date", example = "Date as long milisecond(s)")
    private Long createdDate;

    @ApiModelProperty(name = "Created By", example = "User Admin / admin@admin.net")
    private String createdBy;

}
