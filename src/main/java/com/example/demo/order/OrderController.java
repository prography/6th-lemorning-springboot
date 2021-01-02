package com.example.demo.order;

import com.example.demo.domain.Response;
import com.example.demo.user.JwtUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final JwtUserDetailsService memberService;

//    @PostMapping(value = "/order/{id}")
//    public Response create(Principal principal, @PathVariable("id") Long productId){
//        Response response = new Response();
//        try {
//            Long orderedId = orderService.order(principal.getName(), productId);
//            response.setCode(200);
//            response.setResponse("success");
//            response.setMessage("구매 처리 완료");
//            response.setData(orderService.findById(orderedId));
//        } catch (Exception e) {
//            response.setCode(500);
//            response.setResponse("failed");
//            response.setMessage("구매 처리하는 도중 오류가 발생했습니다.");
//            response.setData(e.toString());
//        }
//        return response;
//    }
}
