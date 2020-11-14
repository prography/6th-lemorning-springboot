package com.example.demo.point;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    @Transactional
    public Point chargePoint(PointChargeDto dto,Long id){
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        dto.setUser(user);

        Point point = PointChargeDto.toEntity(dto);

        Point save = pointRepository.save(point);
        return save;
    }
}
