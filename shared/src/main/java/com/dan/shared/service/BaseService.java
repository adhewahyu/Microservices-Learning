package com.dan.shared.service;

import com.dan.shared.model.request.BaseRequest;
import com.dan.shared.model.response.BaseResponse;


public interface BaseService <I extends BaseRequest, O extends BaseResponse>{

    O execute(I input);

}
