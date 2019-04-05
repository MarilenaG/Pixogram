package com.pixogram.comments.controller;

import com.pixogram.comments.model.Comment;
import com.pixogram.comments.service.CommentService;
import com.pixogram.images.model.Image;
import com.pixogram.images.service.ImageService;
import com.pixogram.users.controller.UserFollowerRepresentation;
import com.pixogram.users.model.User;
import com.pixogram.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/comment")
public class CommentController {

@Autowired CommentService commentService;
@Autowired UserService userService;
@Autowired ImageService imageService;

    @PutMapping("/likeComment")
    public ResponseEntity<CommentRepresentation> likeComment(@RequestParam Long commentId , @RequestParam Long userId) {
        Comment img= commentService.likeComment(commentId,userId);
        CommentRepresentation commentRepresentation = CommentRepresentation.fromComment(img);
        commentRepresentation =fillUserName(commentRepresentation);
        return new ResponseEntity(commentRepresentation, HttpStatus.OK);
    }

    @PutMapping("/dislikeComment")
    public ResponseEntity<CommentRepresentation> dislikeComment(@RequestParam Long commentId , @RequestParam Long userId) {
        Comment img= commentService.dislikeComment(commentId,userId);
        CommentRepresentation commentRepresentation = CommentRepresentation.fromComment(img);
        commentRepresentation =fillUserName(commentRepresentation);
        return new ResponseEntity(commentRepresentation, HttpStatus.OK);
    }
    @PostMapping("/addComment")
    public ResponseEntity<CommentRepresentation> addComment(@RequestBody CommentRepresentation commentRepresentation) {
        Comment comment = new Comment(  );
        comment.setContent(commentRepresentation.content);
        comment.setUser( userService.get(commentRepresentation.userId));
        comment.setImage(imageService.getImage(commentRepresentation.imageId)) ;
        comment.setNoOfLikes(0);
        comment.setNoOfDislikes(0);
        commentService.addComment(comment);
        comment= commentService.getComment(commentRepresentation.id);
        commentRepresentation= CommentRepresentation.fromComment(comment);
        commentRepresentation =fillUserName(commentRepresentation);
        return new ResponseEntity(commentRepresentation, HttpStatus.OK);
    }
    @GetMapping("/showAllCommentsOnImage")
    public ResponseEntity<List<CommentRepresentation>> showAllCommentsOnImage(@RequestParam Long imageId ) {
        Set<Comment> comments= commentService.listAllCommentsByImage(imageId);
        List<CommentRepresentation> results = comments.stream()
                .map(CommentRepresentation::fromComment)
                .map(this::fillUserName)
                .collect(Collectors.toList());
        return ResponseEntity.ok(results);

    }

    private CommentRepresentation fillUserName(CommentRepresentation commentRepresentation){
        commentRepresentation.userName = userService.get(commentRepresentation.userId).getUserName();
        return commentRepresentation;
    }
}
