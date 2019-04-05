package com.pixogram.comments.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.pixogram.images.model.Image;
import com.pixogram.users.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "comments")
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    protected Long id;

    @NotNull
    @JsonProperty
    protected String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @ManyToOne
    @JoinColumn(name ="image_id")
    @NotNull
    private Image image;

    @NotNull
    @JsonProperty
    protected Integer noOfLikes;

    @NotNull
    @JsonProperty
    protected Integer noOfDislikes;

    public Comment() {
    }

    public Comment(@NotNull String content, @NotNull User user, @NotNull Image image) {
        this.content = content;
        this.user = user;
        this.image = image;
        this.noOfDislikes=0;
        this.noOfLikes=0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Integer getNoOfLikes() {
        return noOfLikes;
    }

    public void setNoOfLikes(Integer noOfLikes) {
        this.noOfLikes = noOfLikes;
    }

    public Integer getNoOfDislikes() {
        return noOfDislikes;
    }

    public void setNoOfDislikes(Integer noOfDislikes) {
        this.noOfDislikes = noOfDislikes;
    }
}
