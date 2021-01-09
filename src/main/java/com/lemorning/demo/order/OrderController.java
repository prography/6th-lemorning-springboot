package com.lemorning.demo.order;

import com.lemorning.demo.domain.Response;
import com.lemorning.demo.orderProduct.OrderProduct;
import com.lemorning.demo.user.JwtUserDetailsService;
import com.lemorning.demo.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final JwtUserDetailsService userService;

    @GetMapping(value = "/order/{id}")
    public Response create(Principal principal, @PathVariable("id") Long itemId){
        Response response = new Response();
        try {
            Long orderedId = orderService.order(principal.getName(),itemId);
            response.setCode(200);
            response.setResponse("success");
            response.setMessage("구매 처리 완료");
            response.setData(orderService.findById(orderedId));
        } catch (Exception e) {
            response.setCode(500);
            response.setResponse("failed");
            response.setMessage("구매 처리하는 도중 오류가 발생했습니다.");
            response.setData(e.toString());
        }
        return response;
    }

    /**
     * 구매리스트 조회는 유저가 주문한(order)의 주문 상품들(orderItem)을 보여주는 것이다.
     * 따라서 이중 포문으로 구성되었다.
     */
    @GetMapping("/{userName}/buyingList")
    public Response buyingList(@PathVariable("userName")String email){
        Response response = new Response();
        try {
            User findUser = userService.findByEmail(email);
            List<OrderProduct> buyingList = orderService.findAllByUser(findUser);
            response.setResponse("success");
            response.setMessage(email+"님의 구매리스트입니다.");
            response.setData(buyingList);
        } catch (Exception e) {
            response.setResponse("failed");
            response.setMessage("구매리스트를 조회하는 도중 오류가 발생했습니다.");
            response.setData(e.toString());
        }
        return response;
    }
}
