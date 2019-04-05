package com.pixogram.images.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pixogram.users.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;



@Entity
@Table(name = "images")
public class Image implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    protected Long id;

    @NotNull
    @Size(max = 100)
    @JsonProperty
    protected String title;


    @JsonProperty
    protected String tags;

    @NotNull
    @JsonProperty
    protected String description;

    @NotNull
    @JsonProperty
    protected Integer noOfLikes;

    @NotNull
    @JsonProperty
    protected Integer noOfDislikes;

    @NotNull
    @JsonProperty
    protected String imageStoragePath;

    @JsonProperty
    protected Boolean defaultImage;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    public Image() {
        this.title="";
        this.description="";
        this.imageStoragePath="";
        this.noOfDislikes=0;
        this.noOfLikes=0;
        this.defaultImage= false;

    }

    public Image(@NotNull @Size(max = 100) String title, @NotNull String description, @NotNull String imageStoragePath) {
        this.title = title;
        this.description = description;
        this.imageStoragePath = imageStoragePath;
        this.noOfDislikes=0;
        this.noOfLikes=0;
        this.defaultImage= false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNoOfLikes() {
        return noOfLikes;
    }

    public void setNoOfLikes(Integer noOfLikes) {
        this.noOfLikes = noOfLikes;
    }

    public String getImageStoragePath() {
        return imageStoragePath;
    }

    public void setImageStoragePath(String imageStoragePath) {
        this.imageStoragePath = imageStoragePath;
    }

    public Integer getNoOfDislikes() {
        return noOfDislikes;
    }

    public void setNoOfDislikes(Integer noOfDislikes) {
        this.noOfDislikes = noOfDislikes;
    }

    public Boolean getDefaultImage() {
        return defaultImage;
    }

    public void setDefaultImage(Boolean defaultImage) {
        this.defaultImage = defaultImage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
