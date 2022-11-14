package com.dan.msmasterdata.model.request.province;

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
public class DeleteProvinceRequest extends BaseRequest {

    @ApiModelProperty(example ="1", value="ID Reference to Find")
    private String id;

    @ApiModelProperty(name = "Updated By", example = "User Admin / admin@admin.net")
    private String updatedBy;

    @ApiModelProperty(name = "Updated Date", example = "Date as long milisecond(s)")
    private Long updatedDate;

}
