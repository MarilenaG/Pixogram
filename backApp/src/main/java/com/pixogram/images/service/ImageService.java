package com.pixogram.images.service;

import com.pixogram.activities.infrastructure.ActivityRepository;
import com.pixogram.activities.model.Activity;
import com.pixogram.activities.model.ActivityType;
import com.pixogram.images.infrastructure.ImageRepository;
import com.pixogram.images.model.Image;
import com.pixogram.users.infrastructure.UserRepository;
import com.pixogram.users.model.User;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Component
@Transactional
public class ImageService {

    private ImageRepository imageRepository;
    private ActivityRepository activityRepository;
    private UserRepository userRepository;

    public ImageService(ImageRepository imageRepository, ActivityRepository activityRepository, UserRepository userRepository) {
        this.imageRepository = imageRepository;
        this.activityRepository = activityRepository;
        this.userRepository = userRepository;
    }


    public Integer countLikesOnImagesByUser (Long userId){
        return imageRepository.findByUser_Id(userId).stream()
                .mapToInt(Image::getNoOfLikes)
                .sum();
    }

    public Integer countDislikesOnImagesByUser (Long userId){
        return imageRepository.findByUser_Id(userId).stream()
                .mapToInt(Image::getNoOfLikes)
                .sum();
    }

    public Integer countImagesByUser (Long userId){
        return imageRepository.findByUser_Id(userId).size();
    }

    public Set<Image> listAllImagesByUser(Long userId){
        return  imageRepository.findByUser_Id(userId);
    }

    public Image setDefaultimage( Long ImageId, Long userId){
        Optional<Image> oldDefaultImage = imageRepository.findByDefaultImageAndUser_Id(true,userId);
        if ( oldDefaultImage.isPresent()) {
            oldDefaultImage.get().setDefaultImage(false);
            imageRepository.save(oldDefaultImage.get());
        }

        Image image = imageRepository.findById(ImageId).orElseThrow(()-> new NoSuchElementException("No Image with id "+ ImageId));
        image.setDefaultImage(true);
        imageRepository.save(image);
        return image;
    }

    public Image likeImage( Long ImageId, Long userId){
        Image image = imageRepository.findById(ImageId).orElseThrow(()-> new NoSuchElementException("No Image with id "+ ImageId));
        image.setNoOfLikes( image.getNoOfLikes() + 1 );
        imageRepository.save(image);
        User user = userRepository.findById(userId).orElseThrow(()-> new NoSuchElementException("No user with Id"+ userId));
        Activity activity = new Activity(user, ActivityType.LIKED, image);
        activityRepository.save( activity);
        return  image;
    }

    public Image dislikeImage( Long imageId, Long userId){
        Image image = imageRepository.findById(imageId).orElseThrow(()-> new NoSuchElementException("No Image with id "+ imageId));
        image.setNoOfDislikes( image.getNoOfDislikes() + 1 );
        imageRepository.save(image);
        User user = userRepository.findById(userId).orElseThrow(()-> new NoSuchElementException("No user with Id"+ userId));
        Activity activity = new Activity(user, ActivityType.DISLIKED, image);
        activityRepository.save( activity);
        return  image;
    }

    public Image getDefaultImageByUser(Long userId){
        return imageRepository.findByDefaultImageAndUser_Id(true,userId).orElse(new Image());
    }

    public Image getImage(Long imageId){
        return imageRepository.findById(imageId).orElseThrow(()-> new NoSuchElementException("No image with Id"+ imageId));
    }
    public Image addimage( String  title, String description, String imageStoragePath, Long userId){
        User user = userRepository.findById(userId).orElseThrow(()-> new NoSuchElementException("No user with Id"+ userId));
        Image img = new Image(title,description,imageStoragePath);
        img.setUser(user);
        imageRepository.save(img);
        Activity activity = new Activity(user, ActivityType.ADDED, img);
        activityRepository.save( activity);
        return  img;
    }

    public Image updateImage( Image img) {
       return imageRepository.save(img);
    }
}
