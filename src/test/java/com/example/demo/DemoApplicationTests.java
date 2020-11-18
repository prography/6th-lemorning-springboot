package com.example.demo;

import com.example.demo.upload.S3Service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	S3Service s3Service;

	@Test
	void contextLoads() {
	}

	@Test
	public void  S3_폴더_업로드() throws IOException {
		MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
		String url = s3Service.upload("", firstFile);
		System.out.println(url);
		assertTrue(url.contains("https://lemorning-static.s3.ap-northeast-2.amazonaws.com/"));
	}

}
