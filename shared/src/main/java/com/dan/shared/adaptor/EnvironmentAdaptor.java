package com.dan.shared.adaptor;

import com.dan.shared.model.request.BaseRequest;
import com.dan.shared.model.response.BaseResponse;

public interface EnvironmentAdaptor <I extends BaseRequest, O extends BaseResponse> {

    O execute(I input);

}