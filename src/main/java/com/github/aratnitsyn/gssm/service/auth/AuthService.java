package com.github.aratnitsyn.gssm.service.auth;

import com.github.aratnitsyn.gssm.db.schema.common.UserEntity;
import com.github.aratnitsyn.gssm.service.BaseRestService;
import com.github.aratnitsyn.gssm.web.message.auth.SignInResponseMessage;
import com.github.aratnitsyn.gssm.web.message.auth.SignInRequestMessage;
import com.github.aratnitsyn.gssm.web.message.auth.SignUpRequestMessage;

public interface AuthService extends BaseRestService {
    SignInRequestMessage signIn(SignInResponseMessage requestMessage);

    UserEntity signUp(SignUpRequestMessage requestMessage);
}
