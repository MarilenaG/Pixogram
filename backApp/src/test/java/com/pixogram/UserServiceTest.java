package com.pixogram;

import com.pixogram.users.model.User;
import com.pixogram.users.infrastructure.UserRepository;
import com.pixogram.users.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Before
    public void initData(){
        userRepository.deleteAll();
        User someUser = userRepository.save(new User("someone@company.com","some", "user","xxxxx", true));
        userRepository.save(someUser);
    }
    @Test
    public void getUserByName_should_return_OK_for_existing_user() {
       assert(userService.getUser("someone@company.com")!= null);
    }
}
