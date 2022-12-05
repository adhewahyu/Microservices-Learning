package com.dan.msmasterdata.model.response.city;

import com.dan.shared.model.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityListResponse extends BaseResponse {

    private transient List<CityResponse> cityResponseList;

}
