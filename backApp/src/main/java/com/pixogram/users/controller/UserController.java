package com.pixogram.users.controller;



import com.pixogram.comments.service.CommentService;
import com.pixogram.images.service.ImageService;
import com.pixogram.users.model.User;
import com.pixogram.users.service.SignupService;

import com.pixogram.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin//(origins = "http://localhost:8091")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private SignupService signupService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody UserRepresentation usertoSignup) {
        signupService.signup(usertoSignup.toUser());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/confirmRegistration")
    public ResponseEntity<UserRepresentation> confirmRegistration(@RequestParam @NotNull String userName , @RequestParam @NotNull  String registrationCode ) {
        if (! signupService.confirmApplicationMember(userName,registrationCode, false)) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY, "UserName/registration code mismatch");
        }
        User registeredUser = userService.getUser(userName);
        return new ResponseEntity(UserRepresentation.fromUser(registeredUser), HttpStatus.OK);


    }
    @GetMapping("/hello")
    public ResponseEntity<String> hello(){
        return new ResponseEntity("hello there from the back", HttpStatus.OK);
    }

    @GetMapping("/{myUserId}/users")
    public ResponseEntity<List<UserRepresentation>> listAllUsers (@PathVariable Long myUserId) {
        List<User> allUsers = userService.listAllUsers();
        User myUser = userService.get(myUserId);
        List<UserRepresentation> results = allUsers.stream()
                .map(usr->UserRepresentation.fromUserVsMe(usr,myUser))
                .collect(toList());
        return ok(results);
    }

    @GetMapping("/{id}/followers")
    public ResponseEntity<List<UserFollowerRepresentation>> listFollowers (@PathVariable Long id) {
        Set<User> followers = userService.get(id).getFollowers();
        List<UserFollowerRepresentation> results = followers.stream()
                .map(UserFollowerRepresentation::fromUser)
                .map(t->fillNoOfComments(t))
                .map(t->fillNoOfImages(t))
                .map(t->fillNoOfLikes(t))
                .map(t->fillNoOfDislikes(t))
                .collect(toList());
        return ok(results);
    }

    @GetMapping("/{id}/following")
    public ResponseEntity<List<UserFollowerRepresentation>> listFollowing (@PathVariable Long id) {
        Set<User> followedUsers = userService.get(id).getFollowedUsers();
        List<UserFollowerRepresentation> results = followedUsers.stream()
                .map(UserFollowerRepresentation::fromUser)
                .map(t->fillNoOfComments(t))
                .map(t->fillNoOfImages(t))
                .map(t->fillNoOfLikes(t))
                .map(t->fillNoOfDislikes(t))
                .map(t->fillDefaultImage(t))
                .collect(toList());
        return ok(results);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserRepresentation> getUser (@PathVariable Long id) {
        User foundUser = userService.get(id);
        UserRepresentation userRepresentation = UserRepresentation.fromUser(foundUser);
        userRepresentation.defaultImageDownloadUrl = imageService.getDefaultImageByUser(id).getImageStoragePath();
        return ok(userRepresentation);
    }

    @PostMapping("/updateUser")
    public ResponseEntity<UserRepresentation> updateUser(@RequestBody UserRepresentation userRepresentation ) {
        User updatedUser = userService.updateUser(userRepresentation.toUser());
        return new ResponseEntity(updatedUser, HttpStatus.OK);
    }

    @PostMapping("/followUser")
    public ResponseEntity<UserRepresentation> followUser( @RequestParam Long myUserId,@RequestParam Long userIdToFollow  ) {
        User updatedUser = userService.followUser(myUserId, userIdToFollow);
        return new ResponseEntity(updatedUser, HttpStatus.OK);
    }

    @PostMapping("/unfollowUser")
    public ResponseEntity<UserRepresentation> unfollowUser( @RequestParam Long myUserId,@RequestParam Long userIdToUnfollow  ) {
        User updatedUser = userService.unfollowUser(myUserId, userIdToUnfollow);
        return new ResponseEntity(updatedUser, HttpStatus.OK);
    }

    private UserFollowerRepresentation fillNoOfComments(UserFollowerRepresentation userFollowerRepresentation){
        userFollowerRepresentation.noOfComments = commentService.countCommentsByUser(userFollowerRepresentation.id);
        return userFollowerRepresentation;
    }
    private UserFollowerRepresentation fillNoOfImages(UserFollowerRepresentation userFollowerRepresentation){
        userFollowerRepresentation.noOfImages = imageService.countImagesByUser(userFollowerRepresentation.id);
        return userFollowerRepresentation;
    }

    private UserFollowerRepresentation fillNoOfLikes(UserFollowerRepresentation userFollowerRepresentation){
        userFollowerRepresentation.noOfLikes =
                imageService.countLikesOnImagesByUser(userFollowerRepresentation.id) +
                commentService.countLikesOnCommentsByUser(userFollowerRepresentation.id);
        return userFollowerRepresentation;
    }

    private UserFollowerRepresentation fillNoOfDislikes(UserFollowerRepresentation userFollowerRepresentation){
        userFollowerRepresentation.noOfDislikes =
                imageService.countDislikesOnImagesByUser(userFollowerRepresentation.id) +
                        commentService.countDislikesOnCommentsByUser(userFollowerRepresentation.id);
        return userFollowerRepresentation;
    }
    private UserFollowerRepresentation fillDefaultImage(UserFollowerRepresentation userFollowerRepresentation){
        userFollowerRepresentation.imageLocation =
                imageService.getDefaultImageByUser(userFollowerRepresentation.id).getImageStoragePath();
        return userFollowerRepresentation;
    }

}
