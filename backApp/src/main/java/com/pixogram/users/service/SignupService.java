package com.pixogram.users.service;

import com.pixogram.config.MailClientService;
import com.pixogram.users.infrastructure.UserRepository;
import com.pixogram.users.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Component
@Transactional
public class SignupService {


    private UserRepository userRepository;
    private MailClientService mailClientService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private RestTemplate restTemplate;


    public SignupService(UserRepository userRepository, MailClientService mailClientService, BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.userRepository = userRepository;
        this.mailClientService = mailClientService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void signup ( User applicationMember){

        //generate confirmation code and sava to database
        String confirmationCode = generateUserConfirmationCode();
        applicationMember.setRegistrationCode(confirmationCode);
        applicationMember.setPassword(bCryptPasswordEncoder.encode(applicationMember.getPassword()));
        applicationMember.setActive(false);
        System.out.println("Registration code for for user "+applicationMember.getUserName()+ " is "+ confirmationCode);
        userRepository.save((User) applicationMember);


        //send confirmation email
        mailClientService.sendConfirmationSignup(applicationMember.getEmail(), confirmationCode);
    }


    private final static String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final static int USERCONFIRMATION_CODE_LENGHT = 10 ;




    private String generateUserConfirmationCode (){
        int count = USERCONFIRMATION_CODE_LENGHT;
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();

    }

    public boolean confirmApplicationMember(String userName, String registrationCode, boolean isMentor){
        boolean applicationMemberconfirmed = false;


        User user = userRepository.findByUserName(userName).orElseThrow(() -> new NoSuchElementException("No user with name " + userName));
        applicationMemberconfirmed = registrationCode.equals(user.getRegistrationCode());
        if (applicationMemberconfirmed) {
            user.setActive(true);
            userRepository.save(user);
        }

        return applicationMemberconfirmed;

    }
}
