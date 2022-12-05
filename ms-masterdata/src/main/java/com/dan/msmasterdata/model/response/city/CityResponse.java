package com.dan.msmasterdata.model.response.city;

import com.dan.shared.model.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityResponse extends BaseResponse {

    private String id;
    private String cityCode;
    private String cityName;
    private Boolean isDeleted;
    private Boolean isActive;
    private String provinceCode;

}
