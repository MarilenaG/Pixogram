package com.pixogram.comments.controller;

import com.pixogram.comments.model.Comment;
import com.pixogram.comments.service.CommentService;
import com.pixogram.users.controller.UserFollowerRepresentation;
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
