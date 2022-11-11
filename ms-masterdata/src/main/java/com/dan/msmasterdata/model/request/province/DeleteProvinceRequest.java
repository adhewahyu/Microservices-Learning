package com.dan.msmasterdata.model.request.province;

import com.dan.shared.model.request.FindByIdRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteProvinceRequest extends FindByIdRequest {

    @ApiModelProperty(name = "Updated By", example = "User Admin / admin@admin.net")
    private String updatedBy;

    @ApiModelProperty(name = "Updated Date", example = "Date as long milisecond(s)")
    private Long updatedDate;

}
