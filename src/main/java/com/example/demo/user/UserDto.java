package com.example.demo.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
  private String email;
  private String password;
  private String auth;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate birthday;
  private Gender gender;
  private String nickname;
  private String profileImageUrl;
  private MultipartFile profile;
}