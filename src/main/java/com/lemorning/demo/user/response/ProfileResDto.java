package com.lemorning.demo.user.response;

import com.lemorning.demo.user.User;
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

    public static ProfileResDto toDto(User byEmail) {
        return ProfileResDto.builder().email(byEmail.getEmail()).build();
    }
}
