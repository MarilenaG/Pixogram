package com.pixogram.comments.infrastructure;

import com.pixogram.comments.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Set<Comment> findByUser_Id(Long userId);
    Set<Comment> findByImage_Id(Long imageId);

}
