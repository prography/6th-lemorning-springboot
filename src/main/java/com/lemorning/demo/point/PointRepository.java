package com.lemorning.demo.point;

import com.lemorning.demo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface PointRepository extends JpaRepository<Point, Long> {

    List<Point> findAllByUser(User user);

    @Transactional
    @Query("select sum(p.pointAmount) from Point p where p.user = ?1")
    Integer amountSum(User user);
}
