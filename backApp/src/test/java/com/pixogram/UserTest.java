package com.pixogram;

import com.pixogram.users.model.User;
import com.pixogram.users.infrastructure.UserRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void givenUserRepository_whenSaveAndRetreiveUser_thenOK() {
        userRepository.deleteAll();
        User someUser = userRepository.save(new User("sometest", "someone@company.com","user","xxxxx", true));
        someUser.setActive(false);
        userRepository.saveAndFlush(someUser);
        User foundUser = userRepository.findByUserName(someUser.getUserName()).get();

        assert (foundUser!= null);
        assert (foundUser.getId().equals(someUser.getId()));
        assert (!foundUser.getActive());
    }

    @Test
    @Transactional
    public void givenUser_whenFollowerAdded_thenRelationship_bothSides_OK() {
        userRepository.deleteAll();
        User someUser = userRepository.save(new User("someUser","someone@company.com", "xxx","xxxxx", true));
        someUser.setActive(false);
        User someotherUser = userRepository.save(new User("someotherUser","someoneelse@company.com", "xxx","xxxxx", true));
        someotherUser.setActive(false);
        userRepository.saveAndFlush(someotherUser);

        someUser.addFollower(someotherUser);
        userRepository.saveAndFlush(someUser);

        User foundSomeUser = userRepository.findByUserName(someUser.getUserName()).get();
        User foundSomeotherUser = userRepository.findByUserName(someotherUser.getUserName()).get();


        assert (foundSomeUser.getFollowers().size()==1);
        assert (foundSomeUser.getFollowedUsers().size()==0);
        assert (foundSomeotherUser.getFollowers().size()==0);
        assert (foundSomeotherUser.getFollowedUsers().size()==1);
    }
}