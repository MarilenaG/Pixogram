package com.pixogram.auth;

import com.pixogram.users.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
public class LoginController {

    @Autowired UserPrincipalService userPrincipalService;


    @PostMapping("/login")
    public ResponseEntity<UserJwt> login(@RequestBody @NotNull User userToConnect ) {

        User appMember =  userPrincipalService.loadUser(userToConnect.getUserName()) ;
//todo verify password

        String token = JwtTokenUtil.generateToken( new UserPrincipal(appMember));
        UserJwt representation = UserJwt.fromUserPrincipal(appMember);
        representation.setJwtHeader(token);

        return ResponseEntity.ok(representation);



    }
}
