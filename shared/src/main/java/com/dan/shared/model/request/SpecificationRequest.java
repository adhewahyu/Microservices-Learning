package com.dan.shared.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpecificationRequest extends BaseRequest {

    @ApiModelProperty(value="JPA Specification")
    private Specification specification;
    
    @ApiModelProperty(value="JPA Pageable")
    private transient Pageable pageable;
    
}
