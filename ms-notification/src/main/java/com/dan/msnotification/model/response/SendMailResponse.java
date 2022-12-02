package com.dan.msnotification.model.response;

import com.dan.shared.model.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendMailResponse extends BaseResponse {

    private String emailResponse;
    private Boolean successSent;

}
