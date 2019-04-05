package com.pixogram.activities.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pixogram.images.model.Image;
import com.pixogram.users.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "activities_log")
public class Activity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ActivityType activityType;

    @ManyToOne
    @JoinColumn(name ="image_id")
    @NotNull
    private Image image;

    public Activity() {
    }

    public Activity(@NotNull User user, ActivityType activityType, @NotNull Image image) {
        this.user = user;
        this.activityType = activityType;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
