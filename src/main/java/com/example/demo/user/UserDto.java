package com.example.demo.user;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UserDto {
  private String email;
  private String password;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate birthday;
  private Gender gender;
  private String nickname;
  private String profileImageUrl;
  private MultipartFile profile;

  public static User toEntity(UserDto dto) {
    return User.builder()
            .email(dto.getEmail())
            .password(dto.getPassword())
            .birthday(dto.getBirthday())
            .gender(dto.getGender())
            .nickname(dto.getNickname())
            .profileImageUrl(dto.getProfileImageUrl())
            .build();
  }
}