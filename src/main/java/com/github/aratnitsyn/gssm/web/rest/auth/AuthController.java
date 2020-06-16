package com.github.aratnitsyn.gssm.web.rest.auth;

import com.github.aratnitsyn.gssm.db.schema.common.UserEntity;
import com.github.aratnitsyn.gssm.service.auth.SingleAuthService;
import com.github.aratnitsyn.gssm.web.message.auth.SignInRequestMessage;
import com.github.aratnitsyn.gssm.web.message.auth.SignInResponseMessage;
import com.github.aratnitsyn.gssm.web.message.auth.SignUpRequestMessage;
import com.github.aratnitsyn.gssm.web.rest.BaseRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController extends BaseRestController<SingleAuthService> {
    @PostMapping("/signin")
    public ResponseEntity<SignInRequestMessage> signin(@Valid @RequestBody SignInResponseMessage requestMessage) {
        return ResponseEntity.ok(service.signIn(requestMessage));
    }

    @PostMapping("signup")
    public ResponseEntity<UserEntity> signup(@Valid @RequestBody SignUpRequestMessage requestMessage) {
        return ResponseEntity.ok(service.signUp(requestMessage));
    }
}
