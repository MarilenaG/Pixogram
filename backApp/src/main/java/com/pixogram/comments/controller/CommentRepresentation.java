package com.pixogram.comments.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pixogram.comments.model.Comment;
import com.pixogram.images.model.Image;
import com.pixogram.users.model.User;

public class CommentRepresentation {

    @JsonProperty    protected Long id;
    @JsonProperty    protected String content;
    @JsonProperty    protected Long userId;
    @JsonProperty    protected String userName;
    @JsonProperty    protected Long imageId;
    @JsonProperty    protected Integer noOfLikes;
    @JsonProperty    protected Integer noOfDislikes;



    public static CommentRepresentation  fromComment( Comment comment){
        CommentRepresentation commentRepresentation = new CommentRepresentation();
        commentRepresentation.content = comment.getContent();
        commentRepresentation.imageId = comment.getImage().getId();
        commentRepresentation.id = comment.getId();
        commentRepresentation.userId = comment.getUser().getId();
        commentRepresentation.noOfDislikes = comment.getNoOfDislikes();
        commentRepresentation.noOfLikes = comment.getNoOfLikes();

        return commentRepresentation;
    }

    public  Comment  toComment( ){
        Comment comment = new Comment(  );
        comment.setContent(this.content);
        comment.setUser( new User(this.userId));
        comment.setImage(new Image(this.imageId)) ;
        return comment;
    }
}
