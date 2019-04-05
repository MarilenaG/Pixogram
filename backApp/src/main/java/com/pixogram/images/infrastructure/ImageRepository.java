package com.pixogram.images.infrastructure;

import com.pixogram.images.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Set<Image> findByUser_Id(Long userId);
    Optional<Image> findByDefaultImageAndUser_Id(Boolean defaultImage, Long userId);
}
