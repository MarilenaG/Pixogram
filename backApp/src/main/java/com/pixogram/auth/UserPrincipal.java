package com.pixogram.auth;

import com.pixogram.users.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class UserPrincipal  implements UserDetails {
    private String userName;
    private String password;
    private List<GrantedAuthority> roles ;
    private boolean active;


    public UserPrincipal() {
    }

    public UserPrincipal(User user) {
        this.userName = user.getUserName();
        this.password=user.getPassword();
        this.active = user.getActive();
        this.roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));

    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles ;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

  }
