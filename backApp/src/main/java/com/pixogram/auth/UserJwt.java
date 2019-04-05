package com.pixogram.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pixogram.users.model.User;


public class UserJwt {
    @JsonProperty
    private String userName;

    @JsonProperty
    private String jwtHeader;
    @JsonProperty
    private Long id;

    public void setJwtHeader(String jwtHeader) {
        this.jwtHeader = jwtHeader;
    }

    public static UserJwt fromUserPrincipal(User userPrincipal){
        UserJwt representation = new UserJwt();
        representation.userName = userPrincipal.getUserName();
        representation.id = userPrincipal.getId();
        return representation;
    }

}
