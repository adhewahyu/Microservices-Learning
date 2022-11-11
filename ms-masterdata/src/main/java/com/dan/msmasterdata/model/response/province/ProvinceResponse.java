package com.dan.msmasterdata.model.response.province;

import com.dan.shared.model.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProvinceResponse extends BaseResponse {

    private String id;
    private String provinceCode;
    private String provinceName;
    private Boolean isDeleted;
    private Boolean isActive;

}
