package com.dan.shared.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindByIdRequest extends BaseRequest {

    @ApiModelProperty(example ="1", value="ID Reference to Find")
    private String id;
    
}
