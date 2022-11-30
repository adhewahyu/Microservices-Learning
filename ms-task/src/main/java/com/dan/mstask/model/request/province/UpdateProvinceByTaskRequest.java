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
public class UpdateProvinceByTaskRequest extends BaseRequest {

    @ApiModelProperty(example ="1234-1234", value="ID Reference to Find")
    private String id;

    @ApiModelProperty(name = "Is Active", value = "true / false")
    private Boolean isActive;

    @ApiModelProperty(name = "Province Code", example = "JKT")
    private String provinceCode;

    @ApiModelProperty(name = "Province Name", example = "Jakarta")
    private String provinceName;

    @ApiModelProperty(name = "Updated Date", example = "Date as long milisecond(s)")
    private Long updatedDate;

    @ApiModelProperty(name = "Updated By", example = "User Admin / admin@admin.net")
    private String updatedBy;

}
