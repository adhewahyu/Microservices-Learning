package com.dan.msuser.model.response;

import com.dan.shared.model.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse extends BaseResponse {

    private String id;
    private String name;
    private String description;
    private Boolean isDeleted;
    private Boolean isActive;
    private String permissionList;

}
