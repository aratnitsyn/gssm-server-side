package com.github.aratnitsyn.gssm.web.message.auth;

import com.github.aratnitsyn.gssm.web.message.BaseMessage;

/**
 * Request message for storing the client authorization.
 */
public class SignUpRequestMessage extends BaseMessage {
    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
