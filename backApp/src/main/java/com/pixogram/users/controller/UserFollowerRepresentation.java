package com.pixogram.users.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pixogram.images.model.Image;
import com.pixogram.users.model.User;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserFollowerRepresentation {
    @JsonProperty
    public Long id;
    @JsonProperty @NotEmpty
    public String userName;
    @JsonProperty
    public Integer noOfLikes;
    @JsonProperty
    public Integer noOfDislikes;
    @JsonProperty
    public Integer noOfComments;
    @JsonProperty
    public Integer noOfImages;
    @JsonProperty
    public String imageLocation;

    public static UserFollowerRepresentation  fromUser( User user){
        UserFollowerRepresentation userFollowerRepresentation = new UserFollowerRepresentation();
        userFollowerRepresentation.userName = user.getUserName();
        userFollowerRepresentation.id = user.getId();
        return userFollowerRepresentation;
    }

}
