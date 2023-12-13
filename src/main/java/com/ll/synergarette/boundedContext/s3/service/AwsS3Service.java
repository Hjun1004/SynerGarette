package com.ll.synergarette.boundedContext.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ll.synergarette.standard.util.Ut;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
@Service
@Slf4j
public class AwsS3Service {

    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName; //버킷 이름

    public String uploadFile(MultipartFile multipartFile) {
        validateFileExists(multipartFile); //파일이 있는지 확인하는 메서드

        String fileName = Ut.buildFileName(multipartFile.getOriginalFilename()); //fileName

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        try (InputStream inputStream = multipartFile.getInputStream()) {

            amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (Exception e) {
            //기존의 예외 처리
//            log.error("Can not upload image, ", e);
//            throw new RuntimeException("cannot upload image);
            // 예외 처리는 따로 해주는 게 좋습니다.
            throw new RuntimeException(e);
        }
        String url = amazonS3Client.getUrl(bucketName, fileName).toString();

        return url;
    }

    private void validateFileExists(MultipartFile multipartFile) {
        if(multipartFile.isEmpty()) {
            throw new RuntimeException("file is empty");
        }
    }
}
