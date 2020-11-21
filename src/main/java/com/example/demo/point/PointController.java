package com.example.demo.point;

import com.example.demo.creditcard.CreditCardInfo;
import com.example.demo.creditcard.CreditCardService;
import com.example.demo.domain.Response;
import com.example.demo.request.ChargePointRequestDto;
import com.example.demo.response.IdResponseDto;
import com.example.demo.response.SearchPointResponseDto;
import com.example.demo.user.JwtUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/point")
public class PointController {

    private final JwtUserDetailsService userService;
    private final PointService pointService;
    private final CreditCardService cardService;

    @PostMapping("/charge/{point}")
    public Response<IdResponseDto> charge(@PathVariable("point") int point, @RequestBody ChargePointRequestDto request) {
        Response<IdResponseDto> response = new Response<>();

        try {
            Long chargeId = cardService.chargePoint(request.getEmail(), request.getCardNum(), point);

            response.setCode(200);
            response.setResponse("success");
            response.setMessage("포인트 충전에 성공하였습니다.");
            response.setData(new IdResponseDto(chargeId));
        } catch (Exception e) {
            response.setCode(500);
            response.setResponse("failed");
            response.setMessage("포인트 충전 도중 오류가 발생했습니다.");
            response.setError(e.toString());
        }

        return response;
    }

    @GetMapping("/list")
    public Response<List<SearchPointResponseDto>> list() {
        Response<List<SearchPointResponseDto>> response = new Response<>();

        try {
            List<Point> points = pointService.findAll();
            List<SearchPointResponseDto> pointResponse = new ArrayList<>();

            for (Point point : points) {
                CreditCardInfo card = cardService.findOne(point.getCreditCard().getId());
                pointResponse.add(new SearchPointResponseDto(point.getPoint(), card.getCreditCardBank(), card.getCardNickName(), point.getChargeDateTime()));
            }

            response.setCode(200);
            response.setResponse("success");
            response.setMessage("포인트 충전 내역 전체 조회에 성공하였습니다.");
            response.setData(pointResponse);
        } catch (Exception e) {
            response.setCode(500);
            response.setResponse("failed");
            response.setMessage("포인트 충전 도중 오류가 발생했습니다.");
            response.setError(e.toString());
        }

        return response;
    }
}
