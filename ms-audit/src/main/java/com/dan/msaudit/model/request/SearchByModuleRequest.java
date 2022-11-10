package com.dan.msaudit.model.request;

import com.dan.shared.model.request.SearchRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchByModuleRequest extends SearchRequest {

    @ApiModelProperty(name = "module", example = "MY-MODULE")
    private String module;

}
