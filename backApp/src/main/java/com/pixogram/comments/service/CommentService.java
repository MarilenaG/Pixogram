package com.pixogram.comments.service;

import com.pixogram.activities.infrastructure.ActivityRepository;
import com.pixogram.activities.model.Activity;
import com.pixogram.activities.model.ActivityType;
import com.pixogram.comments.infrastructure.CommentRepository;
import com.pixogram.comments.model.Comment;
import com.pixogram.users.infrastructure.UserRepository;
import com.pixogram.users.model.User;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Set;

@Component
@Transactional
public class CommentService {
    private CommentRepository commentRepository;
    private ActivityRepository activityRepository;
    private UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, ActivityRepository activityRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.activityRepository = activityRepository;
        this.userRepository = userRepository;
    }

    public Comment addComment(Comment comment) {
        commentRepository.save(comment);
        Activity activity = new Activity(comment.getUser(), ActivityType.COMMENT, comment.getImage());
        activityRepository.save( activity);
        return comment;
    }
    public Comment likeComment( Long commentId, Long userId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new NoSuchElementException("No comment with id "+ commentId));
        comment.setNoOfLikes( comment.getNoOfLikes() + 1 );
        commentRepository.save(comment);
        User user = userRepository.findById(userId).orElseThrow(()-> new NoSuchElementException("No user with Id"+ userId));
        Activity activity = new Activity(user, ActivityType.LIKED, comment.getImage()); // should be like on comment
        activityRepository.save( activity);
        return  comment;
    }

    public Comment dislikeComment( Long commentId, Long userId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new NoSuchElementException("No comment with id "+ commentId));
        comment.setNoOfDislikes( comment.getNoOfDislikes() + 1 );
        commentRepository.save(comment);
        User user = userRepository.findById(userId).orElseThrow(()-> new NoSuchElementException("No user with Id"+ userId));
        Activity activity = new Activity(user, ActivityType.DISLIKED, comment.getImage()); // should be dislike on comment
        activityRepository.save( activity);
        return  comment;
    }

    public Set<Comment> listAllCommentsByUser(Long userId){
        return  commentRepository.findByUser_Id(userId);
    }

    public Integer countCommentsByUser (Long userId){
        return commentRepository.findByUser_Id(userId).size();
    }

    public Integer countLikesOnCommentsByUser (Long userId){
        return commentRepository.findByUser_Id(userId).stream()
                .mapToInt(Comment::getNoOfLikes)
                .sum();
    }
    public Integer countDislikesOnCommentsByUser (Long userId){
        return commentRepository.findByUser_Id(userId).stream()
                .mapToInt(Comment::getNoOfDislikes)
                .sum();
    }
    public  Set<Comment>  listAllCommentsByImage (Long imageId){
        return commentRepository.findByImage_Id(imageId);
    }

    public Integer countCommentsByImage (Long imageId){
        return commentRepository.findByImage_Id(imageId).size();
    }

    public Integer countLikesOnCommentsByImage (Long imageId){
        return commentRepository.findByImage_Id(imageId).stream()
                .mapToInt(Comment::getNoOfLikes)
                .sum();
    }
    public Integer countDislikesOnCommentsByImage (Long imageId){
        return commentRepository.findByImage_Id(imageId).stream()
                .mapToInt(Comment::getNoOfDislikes)
                .sum();
    }
}
