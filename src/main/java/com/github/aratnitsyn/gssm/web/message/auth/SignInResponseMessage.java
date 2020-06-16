package com.github.aratnitsyn.gssm.web.message.auth;

import com.github.aratnitsyn.gssm.web.message.BaseMessage;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Request message for storing the user's credentials.
 */
public class SignInResponseMessage extends BaseMessage {
    @NotNull
    @Size(min = 1, max = 64)
    private String username;

    @NotNull
    @Size(min = 4, max = 64)
    private String password;

    private Boolean rememberMe = false;

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

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}
