package com.pixogram.activities.infrastructure;

import com.pixogram.activities.model.Activity;
import com.pixogram.comments.model.Comment;
import com.pixogram.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    Set<Activity> findByUser_UserName(String userName);
    Set<Activity> findByUser_Id(Long userId);

}
