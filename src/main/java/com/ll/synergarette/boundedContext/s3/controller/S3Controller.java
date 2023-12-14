package com.ll.synergarette.boundedContext.s3.controller;

import com.ll.synergarette.boundedContext.s3.service.AwsS3Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/tui-editor")
@RequiredArgsConstructor
public class S3Controller {
    @Autowired
    private AwsS3Service service;

//    @RequestParam final MultipartFile image
//    @RequestParam(value = "file") MultipartFile file
    @PostMapping("/image-upload")
    public ResponseEntity<String> uploadFile(@RequestParam final MultipartFile image) {
        return new ResponseEntity<>(service.uploadFile(image), HttpStatus.OK);
    }
}
