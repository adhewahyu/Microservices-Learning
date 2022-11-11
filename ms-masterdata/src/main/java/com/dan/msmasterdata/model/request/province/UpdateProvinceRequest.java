package com.dan.msmasterdata.model.request.province;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProvinceRequest extends BaseProvinceRequest{

    @ApiModelProperty(example ="1", value="ID Reference to Find")
    private String id;

    @ApiModelProperty(name = "Is Active", value = "true / false")
    private Boolean isActive;

    @ApiModelProperty(name = "Is Deleted", value = "true / false")
    private Boolean isDeleted;

    @ApiModelProperty(name = "Updated Date", example = "Date as long milisecond(s)")
    private Long updatedDate;

    @ApiModelProperty(name = "Updated By", example = "User Admin / admin@admin.net")
    private String updatedBy;

}
