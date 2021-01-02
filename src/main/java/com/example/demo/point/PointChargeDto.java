package com.example.demo.point;

import com.example.demo.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointChargeDto {
    private int pointAmount;
    private LocalDateTime pointDateTime;
    private User user;

    public PointChargeDto(int point) {
        this.pointAmount = point;
    }

    public static Point toEntity(PointChargeDto dto) {
        return null;
//        return Point.builder()
////                .pointAmount(dto.pointAmount)
//                .pointDateTime(LocalDateTime.now())
//                .user(dto.user)
//                .build();
    }
}
