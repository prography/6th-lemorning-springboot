package com.example.demo.user.response;

import com.example.demo.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResDto {

    private String email;
    private String nickname;

    public static ProfileResDto toDto(User byEmail) {
        return ProfileResDto.builder()
                .email(byEmail.getEmail())
                .nickname(byEmail.getNickname())
                .build();
    }
}
