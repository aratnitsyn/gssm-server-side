package com.github.aratnitsyn.gssm.web.message.auth;

import com.github.aratnitsyn.gssm.web.message.BaseMessage;

/**
 * Response message for storing the user's token authentication.
 */
public class SignInRequestMessage extends BaseMessage {
    private String token;

    public SignInRequestMessage(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
