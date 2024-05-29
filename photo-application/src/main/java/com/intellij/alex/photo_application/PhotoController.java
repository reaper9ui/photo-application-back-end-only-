package com.intellij.alex.photo_application;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.*;

@RestController
public class PhotoController {

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService){
        this.photoService = photoService;
    }


    @GetMapping("/")
    public String hello(){
        return "Hello World";
    }

    @GetMapping("/photos")
    public Collection<Photo> get(){
        return photoService.get();
    }

    @GetMapping("/photos/{id}")
    public Photo getId(@PathVariable String id){
        Photo photo = photoService.get(id);
        if(photo==null)throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return photo;
    }

    @DeleteMapping("/photos/{id}")
    public void delete(PathVariable id){
        Photo photo = photoService.remove(id);
        if(photo==null)throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/photos/")
    public Photo create(@RequestBody MultipartFile file) throws IOException {
        Photo photo = photoService.save(file.getOriginalFilename(), file.getContentType(), file.getBytes());
        return photo;
    }

}
