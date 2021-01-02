package com.example.demo.point;

import com.example.demo.creditcard.CreditCardInfo;
import com.example.demo.creditcard.CreditCardRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointService {

    private final CreditCardRepository cardRepository;
    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    @Transactional
    public void save(Point p) {
        pointRepository.save(p);
    }

    public List<Point> findAll() {
        return pointRepository.findAll();
    }

    public int getTotalPointByCard(String cardNum) {
        CreditCardInfo card = cardRepository.findBycardNum(cardNum).orElseThrow(EntityNotFoundException::new);

        return pointRepository.getTotalPointByCreditCard(card.getId());
    }

    public int getTotalPointByUser(String userMail) {
        User user = userRepository.findByEmail(userMail).orElseThrow(EntityNotFoundException::new);
        List<CreditCardInfo> cardList = cardRepository.findByUser(user.getId());

        int total = 0;
        for (CreditCardInfo creditCardInfo : cardList)
            total += pointRepository.getTotalPointByCreditCard(creditCardInfo.getId());

        return total;
    }
}
