package com.dan.shared.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsbLazyResponse extends BaseResponse {

    private String body;
    private Integer httpStatus;

}