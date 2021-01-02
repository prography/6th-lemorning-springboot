package com.example.demo.point;

import com.example.demo.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Point {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    private Long id;

    private int pointAmount;

    private LocalDateTime pointDateTime;

    @JsonIgnore
    // 연관관계의 주인
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    public Point(int point) {
        this.pointAmount = point;
    }

    public void updateUser(User user) {
        this.user = user;
        user.getPointList().add(this);
    }
}
