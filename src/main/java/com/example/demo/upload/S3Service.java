package com.example.demo.upload;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Service
@NoArgsConstructor
public class S3Service {
    private AmazonS3 s3;

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    //S3클라이언트 생성
    @PostConstruct
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }

    //업로드
    public String upload(String currentFilePath, MultipartFile file) throws IOException {
        SimpleDateFormat date = new SimpleDateFormat("yyyymmddHHmmss");
        String fileName = file.getOriginalFilename() + "-" + date.format(new Date());

        if (!"".equals(currentFilePath) && currentFilePath != null) {
            boolean isExistObject = s3.doesObjectExist(bucket, currentFilePath);
            if (isExistObject) {
                s3.deleteObject(bucket, currentFilePath);
            }
        }
        LocalDate date1 = LocalDate.now();
        String folderString = date1.getYear()+"/"+date1.getMonthValue()+"/"+date1.getDayOfMonth()+"/";
        s3.putObject(new PutObjectRequest(bucket, folderString+fileName, file.getInputStream(), null)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return s3.getUrl(bucket, folderString+fileName).toString();
    }

    public void deleteFile(String filePath) throws AmazonServiceException {
        try{
            s3.deleteObject(bucket, filePath);
        }
        catch (AmazonServiceException e){
            e.printStackTrace();
        }
    }
}