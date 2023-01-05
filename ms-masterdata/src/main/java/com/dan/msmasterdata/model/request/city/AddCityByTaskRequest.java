package com.dan.msmasterdata.model.request.city;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddCityByTaskRequest extends BaseCityRequest {

    @ApiModelProperty(example ="1234-1234", value="ID Reference to Find")
    private String id;

    @ApiModelProperty(name = "Is Active", value = "true / false")
    private Boolean isActive;

    @ApiModelProperty(name = "Is Deleted", value = "true / false")
    private Boolean isDeleted;

    @ApiModelProperty(name = "Created Date", example = "Date as long milisecond(s)")
    private Long createdDate;

    @ApiModelProperty(name = "Created By", example = "User Admin / admin@admin.net")
    private String createdBy;

}
