package com.example.demo.user;

import com.example.demo.config.JwtTokenUtil;
import com.example.demo.domain.Response;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final JwtUserDetailsService userService;

    private final JwtTokenUtil jwtTokenUtil;

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
            UserInfoDto dto = new UserInfoDto(findUser.getEmail(),findUser.getPoint());
            response.setData(dto);
        } catch (Exception e) {
            response.setResponse("failed");
            response.setMessage("포인트 충전 하는 도중 오류가 발생했습니다.");
            response.setData(e.toString());
        }
        return response;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter @Setter
    class UserInfoDto{
        String name;
        int point;
    }
}