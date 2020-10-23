package com.example.demo.user;

import com.example.demo.config.JwtTokenUtil;
import com.example.demo.domain.Response;
import com.example.demo.shop.Product;
import com.example.demo.shop.ProductService;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final JwtUserDetailsService userService;

    private final JwtTokenUtil jwtTokenUtil;

    private final ProductService productService;



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
            userService.save(infoDto);
            response.setResponse("success");
            response.setMessage("회원가입을 성공적으로 완료했습니다.");
        } catch (Exception e) {
            response.setResponse("failed");
            response.setMessage("회원가입을 하는 도중 오류가 발생했습니다.");
            response.setData(e.toString());
        }
        return response;
    }
    @PostMapping("/signup2")
    public Response signup2(@RequestBody UserDto dto){
        Response response = new Response();
        try {
            userService.updateUserInfo(dto);
            response.setResponse("success");
            response.setMessage("회원가입2을 성공적으로 완료했습니다.");
        }catch (Exception e) {
            response.setResponse("failed");
            response.setMessage("회원가입2을 하는 도중 오류가 발생했습니다.");
            response.setData(e.toString());
        }
        return response;
    }

    @GetMapping("/user/mypage/charge/{point}")
    public @ResponseBody Response chargePoint(@PathVariable("point")int point, Principal principal){
        Response response = new Response();
        try {
            userService.addPoint(principal.getName(),point);
            response.setCode(200);
            response.setResponse("success");
            response.setMessage("포인트 충전을 성공적으로 완료했습니다.");
            User findUser = userService.findByEmail(principal.getName());
            UserPointResponse dto = new UserPointResponse(findUser.getEmail(), findUser.getPoint());
            response.setData(dto);
        } catch (Exception e) {
            response.setResponse("failed");
            response.setMessage("포인트 충전 하는 도중 오류가 발생했습니다.");
            response.setData(e.toString());
        }
        return response;
    }

    // 포인트 충전을 위한 클래스
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter @Setter
    static class UserPointResponse{
        String name;
        int point;
    }
}