package com.example.demo.creditcard;

import com.example.demo.creditcard.request.CreditCardInfoDto;
import com.example.demo.creditcard.response.SavedCardRes;
import com.example.demo.domain.Response;
import com.example.demo.config.JwtUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/card")
public class CreditCardController {
    private final CreditCardService creditCardService;
    private final JwtUserDetailsService userService;

    /**
     * @Auther 유동관
     * @Date 21/01/03
     * @param dto
     * @param principal 계정정보
     * @return SavedCardRes 타입의 객체. 정보를 한정해서 보여주자
     */
    @PostMapping("/save")
    public Response save(@RequestBody CreditCardInfoDto dto, Principal principal) {
        Response response = new Response();

        try {
            CreditCardInfo savedCard = creditCardService.save(dto,principal);
            SavedCardRes resDto = SavedCardRes.toDto(savedCard);
            response.setCode(200);
            response.setResponse("success");
            response.setMessage("카드 등록에 성공하였습니다.");
            response.setData(resDto);
        } catch (Exception e) {
            response.setCode(500);
            response.setResponse("failed");
            response.setMessage("카드 등록 도중 오류가 발생했습니다.");
            response.setData(e.toString());
        }

        return response;
    }

    @GetMapping("/{id}")
    public Response findOne(@PathVariable("id") Long cardId) {
        Response response = new Response();

        try {
            CreditCardInfo findCard = creditCardService.findOne(cardId);
            SavedCardRes resDto = SavedCardRes.toDto(findCard);
            response.setCode(200);
            response.setResponse("success");
            response.setMessage("카드 조회에 성공하였습니다.");
            response.setData(resDto);
        } catch (Exception e) {
            response.setCode(500);
            response.setResponse("failed");
            response.setMessage("카드 조회 도중 오류가 발생했습니다.");
            response.setData(e.toString());
        }

        return response;
    }

    @PostMapping("/update/{id}")
    public Response update(@PathVariable("id") Long cardId, @RequestBody CreditCardInfoDto dto) {
        Response response = new Response();

        try {
            CreditCardInfo creditCardInfo = CreditCardInfoDto.toEntity(dto);
            CreditCardInfo update = creditCardService.update(cardId, creditCardInfo);
            CreditCardInfoDto creditCardInfoDto = CreditCardInfoDto.toDto(update);
            response.setCode(200);
            response.setResponse("success");
            response.setMessage("카드 수정에 성공하였습니다.");
            response.setData(creditCardInfoDto);
        } catch (Exception e) {
            response.setCode(500);
            response.setResponse("failed");
            response.setMessage("카드 수정 도중 오류가 발생했습니다.");
            response.setData(e.toString());
        }

        return response;
    }

    @PostMapping("/delete/{id}")
    public Response delete(@PathVariable("id") Long cardId, Principal principal) {
        Response response = new Response();

        try {
            creditCardService.delete(cardId,principal);
            response.setCode(200);
            response.setResponse("success");
            response.setMessage("카드 삭제에 성공하였습니다.");
        } catch (Exception e) {
            response.setCode(500);
            response.setResponse("failed");
            response.setMessage("카드 삭제 도중 오류가 발생했습니다.");
            response.setData(e.toString());
        }

        return response;
    }

    @GetMapping("/list")
    public Response list(){
        Response response = new Response();

        try {
            List<CreditCardInfo> cardInfoList = creditCardService.findAll();
            response.setCode(200);
            response.setResponse("success");
            response.setMessage("카드 리스트 조회에 성공하였습니다.");
            response.setData(cardInfoList);
        } catch (Exception e) {
            response.setCode(500);
            response.setResponse("failed");
            response.setMessage("카드 리스트 조회 도중 오류가 발생했습니다.");
            response.setData(e.toString());
        }

        return response;
    }

}


