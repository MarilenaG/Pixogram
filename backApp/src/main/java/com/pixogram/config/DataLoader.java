package com.pixogram.config;

import com.pixogram.images.infrastructure.ImageRepository;
import com.pixogram.images.model.Image;
import com.pixogram.users.infrastructure.UserRepository;
import com.pixogram.users.model.User;

import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Component
public class DataLoader {

    private UserRepository userRepository;
    private ImageRepository imageRepository;



    public DataLoader(UserRepository userRepository, ImageRepository imageRepository) {
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
        loadSomeUsers();

    }

@Transactional
    private void loadSomeUsers(){
        User user1 = new User( "user1", "some.user@training.application",
                "$2a$10$Qsj8PCArhb.2B3M1nN4fnOYVqBpp33jyByDP/gwkTh0X3XYHKWQDO",
                 "xxx",
                 true);

        userRepository.save(user1);
        User user2 = new User( "user2",  "other.user@training.application",
                "$2a$10$Qsj8PCArhb.2B3M1nN4fnOYVqBpp33jyByDP/gwkTh0X3XYHKWQDO",
                "xxx",
                true);


        userRepository.save(user2);
        User user3 = new User( "user3",  "different.user@training.application",
                "$2a$10$Qsj8PCArhb.2B3M1nN4fnOYVqBpp33jyByDP/gwkTh0X3XYHKWQDO",
                "xxx",
                true);

        userRepository.save(user3);

//        user3= userRepository.findByUserName("user3").get();
//        user3.addFollower(userRepository.findByUserName("user1").get());
//        user3.addFollower(userRepository.findByUserName("user2").get());
//        userRepository.save(user3);


        Image img1 = new Image("foto 1 clients", "some foto taken during a client meeting", "http://localhost:8091/fullstack/pixogram/image/downloadImage/IMG_0114.jpg", user3);
        imageRepository.save(img1);
        img1 = new Image("foto 2 clients", "some foto taken during a client meeting", "http://localhost:8091/fullstack/pixogram/image/downloadImage/IMG_0033.jpg", user3);
        imageRepository.save(img1);
        img1 = new Image("foto 3 dinner", "some foto taken during a client dinner", "http://localhost:8091/fullstack/pixogram/image/downloadImage/M1.jpeg", user3);
        imageRepository.save(img1);
        img1 = new Image("cat", "a foto of a cat", "http://localhost:8091/fullstack/pixogram/image/downloadImage/cat.jpeg", user3);
        imageRepository.save(img1);
        img1 = new Image("puppy", "cute puppy dog", "http://localhost:8091/fullstack/pixogram/image/downloadImage/puppy.jpeg", user3);
        img1.setDefaultImage(true);
        imageRepository.save(img1);

        img1 = new Image("camel", "some funny camel", "http://localhost:8091/fullstack/pixogram/image/downloadImage/camel.jpeg", user2);
        img1.setDefaultImage(true);
        imageRepository.save(img1);




    }
}
