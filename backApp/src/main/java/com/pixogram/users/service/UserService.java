package com.pixogram.users.service;

import com.pixogram.users.infrastructure.UserRepository;
import com.pixogram.users.model.User;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Component
@Transactional
public class UserService  {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(String userName){
        return userRepository.findByUserName(userName).orElseThrow(()-> new NoSuchElementException("No user with name "+ userName));
    }

    public List<User> listAllUsers(){
        return  userRepository.findAll();
    }

    public User get(Long userId){
        return userRepository.findById(userId).orElseThrow(()-> new NoSuchElementException("No user with id "+ userId));
    }
    public User updateUser(User user){
        return userRepository.save(user);
    }
    public Set<User> listFollowerOfUser(Long userId){
        User who = userRepository.findById(userId).orElseThrow(()-> new NoSuchElementException("No user with id "+ userId));
        return  who.getFollowers();
    }
    public Set<User> listUsersFollowedBy(Long userId){
        User who = userRepository.findById(userId).orElseThrow(()-> new NoSuchElementException("No user with id "+ userId));
        return  who.getFollowedUsers();
    }

    public User followUser(Long myUserId, Long userIdToFollow){
        User userToFollow = userRepository.findById(userIdToFollow).orElseThrow(()-> new NoSuchElementException("No user with id "+ userIdToFollow));
        User myUser = userRepository.findById(myUserId).orElseThrow(()-> new NoSuchElementException("No user with id "+ myUserId));
        userToFollow.addFollower(myUser);
        return userRepository.save(userToFollow);
    }
    public User unfollowUser(Long myUserId, Long userIdToFollow){
        User userToFollow = userRepository.findById(userIdToFollow).orElseThrow(()-> new NoSuchElementException("No user with id "+ userIdToFollow));
        User myUser = userRepository.findById(myUserId).orElseThrow(()-> new NoSuchElementException("No user with id "+ myUserId));
        userToFollow.removeFollower(myUser);
        return userRepository.save(userToFollow);
    }
}
