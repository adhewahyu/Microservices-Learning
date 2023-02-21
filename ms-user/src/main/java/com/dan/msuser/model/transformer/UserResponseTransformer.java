package com.dan.msuser.model.transformer;

import com.dan.msuser.model.entity.User;
import com.dan.msuser.model.response.UserResponse;
import com.dan.shared.model.transformer.MessageTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserResponseTransformer implements MessageTransformer<User, UserResponse> {

    @Override
    public UserResponse transform(User input) {
        return null;
    }

}
