package com.pixogram.images.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pixogram.images.model.Image;
import com.pixogram.users.model.User;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class ImageRepresentation {

    public Long id;
    @JsonProperty
    @NotEmpty
    public String title;
    @JsonProperty
    public String description;
    @JsonProperty
    public long userId;
    @JsonProperty
    public Integer noOfLikes;
    @JsonProperty
    public Integer noOfDislikes;
    @JsonProperty
    public Integer noOfComments;
    @JsonProperty
    public String imageDownloadPath;
    @JsonProperty
    public boolean defaultImage;



    public static ImageRepresentation  fromImage( Image img){
        ImageRepresentation imageRepresentation = new ImageRepresentation();
        imageRepresentation.description = img.getDescription();
        imageRepresentation.title = img.getTitle();
        imageRepresentation.id = img.getId();
        imageRepresentation.userId = img.getUser().getId();
        imageRepresentation.noOfLikes = img.getNoOfLikes();
        imageRepresentation.noOfDislikes = img.getNoOfDislikes();
        imageRepresentation.imageDownloadPath = img.getImageStoragePath();
        imageRepresentation.defaultImage = img.getDefaultImage();
        return imageRepresentation;
    }
    public  Image  toImage(){
        Image img = new Image();
        img.setDescription(this.description);
        img.setTitle(this.title);
        img.setId(this.id);
        img.setNoOfDislikes(this.noOfDislikes);
        img.setNoOfLikes(this.noOfLikes);
        img.setUser(new User(this.userId));
        img.setDefaultImage(this.defaultImage);
        img.setImageStoragePath(this.imageDownloadPath);

        return img;
    }

}
