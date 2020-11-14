package com.example.demo.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class UserDto {
  private String email;
  private String password;
  private String auth;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate birthday;
  private Gender gender;
  private String nickname;
  private String profileImageUrl;
}