package com.dan.msmasterdata.model.response.province;

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
public class ProvinceListResponse extends BaseResponse {

    private transient List<ProvinceResponse> provinceResponseList;

}
