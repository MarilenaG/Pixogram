package com.pixogram.users.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pixogram.users.model.User;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserRepresentation {
    @JsonProperty private Long id;
    @JsonProperty @NotEmpty  private String userName;
    @JsonProperty  @Email private String email;
    @JsonProperty private String password;
    @JsonProperty private String registrationCode;
    @JsonProperty private Boolean active = true;
    @JsonProperty public String defaultImageDownloadUrl;
    @JsonProperty public Boolean followedByMe;
    @JsonProperty public Boolean followingMe;

    public UserRepresentation(Long id, @NotEmpty @Email String userName, String email, String password, String registrationCode, Boolean active) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.registrationCode = registrationCode;
        this.active = active;
    }

    public UserRepresentation() {
    }
    public User toUser(){
        User user = new User();
        BeanUtils.copyProperties(this, user);
        return user ;
    }

    public static UserRepresentation  fromUser( User user){
        UserRepresentation userRepresentaion = new UserRepresentation();
        BeanUtils.copyProperties(user, userRepresentaion);
        return userRepresentaion;
    }
    public static UserRepresentation  fromUserVsMe( User user, User myUser){
        UserRepresentation userRepresentaion = new UserRepresentation();
        BeanUtils.copyProperties(user, userRepresentaion);
        userRepresentaion.followedByMe= user.getFollowers().contains(myUser.getId());
        userRepresentaion.followingMe=  user.getFollowedUsers().contains(myUser.getId());
        return userRepresentaion;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
