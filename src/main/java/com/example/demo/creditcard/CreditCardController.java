package com.example.demo.creditcard;

import com.example.demo.request.CreateCardRequestDto;
import com.example.demo.request.UpdateCardRequestDto;
import com.example.demo.response.IdResponseDto;
import com.example.demo.response.SearchCardResponseDto;
import com.example.demo.domain.Response;
import com.example.demo.user.JwtUserDetailsService;
import com.example.demo.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/card")
public class CreditCardController {
    private final JwtUserDetailsService userService;
    private final CreditCardService cardService;

    @PostMapping("/save")
    public Response<IdResponseDto> save(@RequestBody CreateCardRequestDto request) {
        Response<IdResponseDto> response = new Response<>();

        try {
            User user = userService.findByEmail(request.getEmail());

            CreditCardInfo creditCardInfo = CreditCardInfo.builder()
                    .creditCardBank(request.getCreditCardBank())
                    .cardNickname(request.getCardNickName())
                    .cardNum(request.getCardNum())
                    .expireYear(request.getExpireYear())
                    .expireMonth(request.getExpireMonth())
                    .user(user)
                    .simplePassword(request.getSimplePassword())
                    .build();

            Long saveId = cardService.save(creditCardInfo);

            response.setCode(200);
            response.setResponse("success");
            response.setMessage("카드 등록에 성공하였습니다.");
            response.setData(new IdResponseDto(saveId));
        } catch (Exception e) {
            response.setCode(500);
            response.setResponse("failed");
            response.setMessage("카드 등록 도중 오류가 발생했습니다.");
            response.setError(e.getMessage());
        }

        return response;
    }

    @GetMapping("/{id}")
    public Response<SearchCardResponseDto> findOne(@PathVariable("id") Long cardId) {
        Response<SearchCardResponseDto> response = new Response<>();

        try {
            CreditCardInfo findCard = cardService.findOne(cardId);

            response.setCode(200);
            response.setResponse("success");
            response.setMessage("카드 조회에 성공하였습니다.");
            response.setData(new SearchCardResponseDto(findCard));
        } catch (Exception e) {
            response.setCode(500);
            response.setResponse("failed");
            response.setMessage("카드 조회 도중 오류가 발생했습니다.");
            response.setError(e.getMessage());
        }

        return response;
    }

    @PostMapping("/update/{id}")
    public Response<IdResponseDto> update(@PathVariable("id") Long cardId, @RequestBody UpdateCardRequestDto request) {
        Response<IdResponseDto> response = new Response<>();

        try {
            Long updateId = cardService.update(cardId, request);

            response.setCode(200);
            response.setResponse("success");
            response.setMessage("카드 수정에 성공하였습니다.");
            response.setData(new IdResponseDto(updateId));
        } catch (Exception e) {
            response.setCode(500);
            response.setResponse("failed");
            response.setMessage("카드 수정 도중 오류가 발생했습니다.");
            response.setError(e.getMessage());
        }

        return response;
    }

    @PostMapping("/delete/{id}")
    public Response<IdResponseDto> delete(@PathVariable("id") Long cardId) {
        Response<IdResponseDto> response = new Response<>();

        try {
            Long deleteId = cardService.delete(cardId);

            response.setCode(200);
            response.setResponse("success");
            response.setMessage("카드 삭제에 성공하였습니다.");
            response.setData(new IdResponseDto(deleteId));
        } catch (Exception e) {
            response.setCode(500);
            response.setResponse("failed");
            response.setMessage("카드 삭제 도중 오류가 발생했습니다.");
            response.setError(e.getMessage());
        }

        return response;
    }

    @GetMapping("/list")
    public Response<List<SearchCardResponseDto>> list() {
        Response<List<SearchCardResponseDto>> response = new Response<>();

        try {
            List<CreditCardInfo> cardInfoList = cardService.findAll();
            List<SearchCardResponseDto> cardResponse = cardInfoList.stream().map(SearchCardResponseDto::new).collect(Collectors.toList());

            response.setCode(200);
            response.setResponse("success");
            response.setMessage("카드 리스트 조회에 성공하였습니다.");
            response.setData(cardResponse);
        } catch (Exception e) {
            response.setCode(500);
            response.setResponse("failed");
            response.setMessage("카드 리스트 조회 도중 오류가 발생했습니다.");
            response.setError(e.getMessage());
        }

        return response;
    }
}
