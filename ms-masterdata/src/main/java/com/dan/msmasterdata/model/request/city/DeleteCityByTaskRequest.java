package com.dan.msmasterdata.model.request.city;

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
public class DeleteCityByTaskRequest extends BaseRequest {

    @ApiModelProperty(example ="1234-1234", value="ID Reference to Find")
    private String id;

    @ApiModelProperty(name = "Updated By", example = "User Admin / admin@admin.net")
    private String updatedBy;

    @ApiModelProperty(name = "Updated Date", example = "Date as long milisecond(s)")
    private Long updatedDate;

}
