package com.example.demo.user;

import com.example.demo.config.JwtTokenUtil;
import com.example.demo.config.JwtUserDetailsService;
import com.example.demo.domain.Response;
import com.example.demo.product.Product;
import com.example.demo.product.ProductService;
import com.example.demo.point.Point;
import com.example.demo.point.PointChargeDto;
import com.example.demo.point.PointService;
import com.example.demo.user.response.ProfileResDto;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final JwtUserDetailsService userService;

    private final PointService pointService;

    private final JwtTokenUtil jwtTokenUtil;

    private final ProductService productService;

    @PostMapping("/profile")
    public Response profile(Principal principal){
        Response response = new Response();
        try {
            User byEmail = userService.findByEmail(principal.getName());
            response.setCode(200);
            response.setResponse("success");
            response.setMessage(byEmail.getEmail()+"님의 프로필입니다.");
            ProfileResDto res = ProfileResDto.toDto(byEmail);
            response.setData(res);
        } catch (Exception e) {
            response.setCode(500);
            response.setResponse("failed");
            response.setMessage("프로필 조회하는 도중 오류가 발생했습니다.");
            response.setData(e.toString());
        }
        return response;
    }

    @GetMapping("/{userName}/sellingList")
    public Response sellingList(@PathVariable("userName")String email){
        Response response = new Response();
        try {
            List<Product> sellingList = userService.findSellingList(email);
            response.setResponse("success");
            response.setMessage(email+"님의 판매리스트입니다.");
            response.setData(sellingList);
        } catch (Exception e) {
            response.setResponse("failed");
            response.setMessage("판매 리스트를 조회하는 도중 오류가 발생했습니다.");
            response.setData(e.toString());
        }
        return response;
    }

    @PostMapping("/signup")
    public Response signup(@RequestBody UserDto infoDto) { // 회원 추가
        Response response = new Response();
        try {
            User user = UserDto.toEntity(infoDto);
            userService.save(user);
            response.setResponse("success");
            response.setMessage("회원가입을 성공적으로 완료했습니다.");
            response.setCode(200);
        } catch (Exception e) {
            response.setResponse("failed");
            response.setMessage("회원가입을 하는 도중 오류가 발생했습니다.");
            response.setData(e.toString());
        }
        return response;
    }

    @GetMapping("/user/mypage/charge/{point}")
    public @ResponseBody Response chargePoint(@PathVariable("point")int point, Principal principal){
        Response response = new Response();
        try {
            Point savedPoint = pointService.chargePoint(new PointChargeDto(point), principal);
            response.setCode(200);
            response.setResponse("success");
            response.setMessage("포인트 충전을 성공적으로 완료했습니다.");
            UserPointResponse dto = UserPointResponse.toDto(savedPoint);
            response.setData(dto);
        } catch (Exception e) {
            response.setResponse("failed");
            response.setMessage("포인트 충전 하는 도중 오류가 발생했습니다.");
            response.setData(e.toString());
        }
        return response;
    }

    // 포인트 충전을 위한 클래스
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class UserPointResponse{
        String email;
        int money;

        public static UserPointResponse toDto(Point savedPoint) {
            return UserPointResponse.builder()
                    .email(savedPoint.getUser().getEmail())
                    .money(savedPoint.getPointAmount())
                    .build();
        }
    }
}