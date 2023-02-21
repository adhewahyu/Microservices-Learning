package com.dan.msuser.model.response;

import com.dan.shared.model.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse extends BaseResponse {

    private String id;
    private String email;
    private String fullname;
    private Boolean isDeleted;
    private Boolean isActive;
    private Date lastLoginDate;
    private String roleId;


}
