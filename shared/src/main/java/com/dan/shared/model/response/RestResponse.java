package com.dan.shared.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestResponse extends BaseResponse{

    private Object data;
    private String message;
    private String messageCode;
    private Boolean result;

}
