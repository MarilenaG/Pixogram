package com.pixogram.users.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    protected Long id;

    @NotNull
    @Size(max = 100)
    @Column(unique = true)
    @JsonProperty
    protected String userName;

    @NotNull
    @Size(max = 100)
    @Column(unique = true)
    @JsonProperty
    @Email
    protected String email;

    @NotNull
    @Size(max = 128)
    @JsonProperty
    protected String password;

    @NotNull
    @Size(max = 128)
    @JsonProperty
    protected String registrationCode;

    @NotNull
    @JsonProperty
    protected Boolean active;

    @ManyToMany(cascade={CascadeType.ALL})
    @JoinTable(name="USER_FOLLOWERS",
            joinColumns={@JoinColumn(name="USER_ID")},
            inverseJoinColumns={@JoinColumn(name="FOLLOWER_ID")})
    private Set<User> followers = new HashSet<>();

    @ManyToMany(mappedBy="followers")
    private Set<User> followedUsers = new HashSet<User>();

    // Hibernate requires a no-arg constructor
    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public User(@NotNull @Size(max = 100) String userName,
                @NotNull @Size(max = 100) @Email String email,
                @NotNull @Size(max = 128) String password,
                @NotNull @Size(max = 128) String registrationCode,
                @NotNull Boolean active) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.registrationCode = registrationCode;
        this.active = active;
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

    public Set<User> getFollowers() {
        return followers;
    }

    public Set<User> getFollowedUsers() {
        return followedUsers;
    }

    public void addFollower(User follower) {
        follower.followedUsers.add(this);
        this.followers.add(follower);
    }

    public void removeFollower(User follower) {
        follower.followedUsers.remove(this);
        this.followers.remove(follower);
    }
    public void addFollowedBy(User followedBy) {
        followedBy.followers.add(this);
        this.followedUsers.add(followedBy);
    }
}
