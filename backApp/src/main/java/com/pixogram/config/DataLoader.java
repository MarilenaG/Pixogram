package com.pixogram.config;

import com.pixogram.users.infrastructure.UserRepository;
import com.pixogram.users.model.User;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader {

    private UserRepository userRepository;



    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;

        loadSomeUsers();

    }


    private void loadSomeUsers(){
        User user = new User( "some", "some.user@training.application",
                "$2a$10$Qsj8PCArhb.2B3M1nN4fnOYVqBpp33jyByDP/gwkTh0X3XYHKWQDO",
                 "xxx",
                 true);

        userRepository.save(user);
        user = new User( "other",  "other.user@training.application",
                "$2a$10$Qsj8PCArhb.2B3M1nN4fnOYVqBpp33jyByDP/gwkTh0X3XYHKWQDO",
                "xxx",
                true);

        userRepository.save(user);
        user = new User( "different",  "different.user@training.application",
                "$2a$10$Qsj8PCArhb.2B3M1nN4fnOYVqBpp33jyByDP/gwkTh0X3XYHKWQDO",
                "xxx",
                true);

        userRepository.save(user);


    }
}
