package com.dan.shared.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsbRequest extends BaseRequest {

    private transient HttpEntity payload;
    private Boolean isPlain;
    private LinkedMultiValueMap<String, String> params;

}