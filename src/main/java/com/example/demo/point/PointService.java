package com.example.demo.point;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    /**
     * 포인트 충전을 검증하는 로직은 이미 마친 후, 충전하는 로직만 작성하였습니다.
     * @FirstDate 21/01/03
     * @Auther 유동관
     * @param dto
     * @param principal
     * @return 충전된 포인트 정보를 리턴합니다.
     */
    public Point chargePoint(PointChargeDto dto, Principal principal){
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        Point point = PointChargeDto.toEntity(dto);
        user.updatePointSum(point.getPointAmount());    // 포인트 합계 추가해준다. 나중에 이걸로 잔액비교해주기 때문에 중요한 로직!
        point.updateUser(user);

        return pointRepository.save(point);
    }

    public int amountSum(Long userId){
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        return pointRepository.amountSum(user);
    }
}
