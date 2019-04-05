package com.pixogram.images.controller;

import com.pixogram.comments.service.CommentService;
import com.pixogram.config.FileStorageService;
import com.pixogram.images.model.Image;
import com.pixogram.images.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = "/image")
public class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private CommentService commentService;

    @PostMapping("/uploadImage")
    public UploadFileResponse uploadImage(@RequestParam("file") MultipartFile file, @RequestParam String title, @RequestParam String description, @RequestParam Long userId) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromUri(URI.create(fileStorageService.getFileDownloadURL()))
                .path("/downloadImage/")
                .path(fileName)
                .toUriString();

        imageService.addimage(title, description, fileDownloadUri,userId);
        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleImages")
    public List<UploadFileResponse> uploadMultipleImages(@RequestParam("files") MultipartFile[] files, @PathVariable String[] titles, @PathVariable String[] descriptions, @PathVariable Long userId) {
        UploadFileResponse resp;
        List<UploadFileResponse> responses = new ArrayList<>();
        for (int i=0; i<files.length; i++){
            resp= uploadImage(files[i],titles[i],descriptions[i],userId);
            responses.add(resp);
            imageService.addimage(titles[i], descriptions[i], resp.getFileDownloadUri(),userId);
        }
        return responses;
    }

    @GetMapping("/downloadImage/{fileName:.+}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PutMapping("/likeImage")
    public ResponseEntity<ImageRepresentation> likeImage(@RequestParam Long imageId , @RequestParam Long userId) {
        Image img= imageService.likeImage(imageId,userId);
        ImageRepresentation imageRepresentation = ImageRepresentation.fromImage(img);
        imageRepresentation.noOfComments = commentService.countCommentsByImage(imageId);

        return new ResponseEntity(imageRepresentation, HttpStatus.OK);
    }

    @PutMapping("/dislikeImage")
    public ResponseEntity<ImageRepresentation> dislikeImage(@RequestParam Long imageId , @RequestParam Long userId) {
        Image img= imageService.dislikeImage(imageId,userId);
        ImageRepresentation imageRepresentation = ImageRepresentation.fromImage(img);
        imageRepresentation.noOfComments = commentService.countCommentsByImage(imageId);

        return new ResponseEntity(imageRepresentation, HttpStatus.OK);
    }

    @PostMapping("/setDefaultImage")
    public ResponseEntity<ImageRepresentation> setDefaultimage(@RequestParam Long imageId , @RequestParam Long userId) {
        Image img= imageService.setDefaultimage(imageId,userId);
        ImageRepresentation imageRepresentation = ImageRepresentation.fromImage(img);
        imageRepresentation.noOfComments = commentService.countCommentsByImage(imageId);
        return new ResponseEntity(imageRepresentation, HttpStatus.OK);
    }

    @PostMapping("/updateImage")
    public ResponseEntity<ImageRepresentation> updateimage(@RequestBody ImageRepresentation imageRepresentation) {
        Image img= imageService.updateImage(imageRepresentation.toImage());
        ImageRepresentation imgRepresentation = ImageRepresentation.fromImage(img);
        imgRepresentation.noOfComments = commentService.countCommentsByImage(img.getId());
        return new ResponseEntity(imgRepresentation, HttpStatus.OK);
    }



    @GetMapping("/getAllImagesForUser")
    public ResponseEntity<List<ImageRepresentation>> getAllImagesForUser (@RequestParam Long userId) {
        Set<Image> images = imageService.listAllImagesByUser(userId);
        List<ImageRepresentation> results = images.stream()
                .map(ImageRepresentation::fromImage)
                .map(t->fillNoOfComments(t))
                .collect(toList());
        return ok(results);
    }
    private ImageRepresentation fillNoOfComments(ImageRepresentation imageRepresentation){
        imageRepresentation.noOfComments =
                commentService.countCommentsByImage(imageRepresentation.id);
        return imageRepresentation;
    }
}
