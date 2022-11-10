package com.dan.msgateway.model.response;

import com.dan.shared.model.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomRouteResponse extends BaseResponse {

    private String id;
    private String path;
    private String url;
    private String apiKey;

}
