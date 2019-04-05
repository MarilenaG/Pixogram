package com.pixogram;

import com.pixogram.activities.infrastructure.ActivityRepository;
import com.pixogram.activities.model.Activity;
import com.pixogram.activities.model.ActivityType;
import com.pixogram.images.infrastructure.ImageRepository;
import com.pixogram.images.model.Image;
import com.pixogram.users.infrastructure.UserRepository;
import com.pixogram.users.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityTest {

    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ImageRepository imageRepository;


    @Test
    public void givenActivityRepository_whenSaveAndRetreiveUser_thenOK() {
        userRepository.deleteAll();
        User someUser = userRepository.save(new User("sometest", "someone@company.com", "user", "xxxxx", true));
        userRepository.saveAndFlush(someUser);
        imageRepository.deleteAll();
        Image image1 = imageRepository.save(new Image("first image", "description", "image path"));
        Image image2 = imageRepository.save(new Image("second image", "description", "image path"));
        imageRepository.saveAndFlush(image1);
        imageRepository.saveAndFlush(image2);

        activityRepository.deleteAll();
        activityRepository.saveAndFlush(new Activity(someUser, ActivityType.ADDED, image1));
        activityRepository.saveAndFlush(new Activity(someUser, ActivityType.COMMENT, image2));
        activityRepository.saveAndFlush(new Activity(someUser, ActivityType.LIKED, image2));
        activityRepository.saveAndFlush(new Activity(someUser, ActivityType.DISLIKED, image1));


        Set<Activity> activityLog = activityRepository.findByUser_Id(someUser.getId());


        assert (activityLog.size() == 4);
        activityLog = activityRepository.findByUser_UserName(someUser.getUserName());
        assert (activityLog.size() == 4);
    }
}