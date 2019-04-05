package com.pixogram.auth;

import com.pixogram.users.infrastructure.UserRepository;
import com.pixogram.users.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class UserPrincipalService implements UserDetailsService {
    private UserRepository userRepository;


    public UserPrincipalService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {


        return new UserPrincipal(loadUser(userName));
    }

    public User loadUser(String userName) throws UsernameNotFoundException {

        User user = userRepository.findByUserName(userName).orElse(null);

        if (user == null ){
            throw new UsernameNotFoundException("No user or user with name " + userName);
        }

        return user;
    }
}
