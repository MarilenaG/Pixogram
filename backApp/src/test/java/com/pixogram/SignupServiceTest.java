package com.pixogram;

import com.pixogram.users.model.User;
import com.pixogram.users.service.SignupService;
import com.pixogram.users.infrastructure.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SignupServiceTest {

    @Autowired
    private SignupService signupService;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void signup_user_should_create_user_inactive(){
        User userToSignup = new User("marilena_matei@yahoo.com", "Marilena", "Gibson","parola",true);
        signupService.signup(userToSignup);
        User savedUser = userRepository.findByUserName("marilena_matei@yahoo.com").get();
        assert (!savedUser.getActive());
        userRepository.delete(userToSignup);
    }

    @Test
    public void confirmation_user_with_incorect_code_should_fail(){
        User userToSignup = new User("marilena_matei@yahoo.com", "Marilena", "Gibson","parola",true);
        signupService.signup(userToSignup);
        User savedUser = userRepository.findByUserName("marilena_matei@yahoo.com").get();

        assert (!signupService.confirmApplicationMember("marilena_matei@yahoo.com","xxx", false));
        userRepository.delete(userToSignup);
    }

    @Test
    public void confirmation_user_with_corect_code_should_succeed(){
        User userToSignup = new User("marilena_matei@yahoo.com", "Marilena", "Gibson","parola",true);
        signupService.signup(userToSignup);
        User savedUser = userRepository.findByUserName("marilena_matei@yahoo.com").get();
        assert (signupService.confirmApplicationMember("marilena_matei@yahoo.com",userToSignup.getRegistrationCode(), false));
        userRepository.delete(userToSignup);
    }
}
