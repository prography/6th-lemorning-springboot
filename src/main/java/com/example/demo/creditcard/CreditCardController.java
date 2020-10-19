package com.example.demo.creditcard;

import com.example.demo.domain.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/card")
public class CreditCardController {
    private final CreditCardService creditCardService;

    @PostMapping("/save")
    public Response save(@RequestBody CreditCardInfo creditCardInfo) {
        Response response = new Response();

        try {
            creditCardService.save(creditCardInfo);
            response.setCode(200);
            response.setResponse("success");
            response.setMessage("카드 등록에 성공하였습니다.");
            response.setData(creditCardInfo);
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
            response.setCode(200);
            response.setResponse("success");
            response.setMessage("카드 조회에 성공하였습니다.");
            response.setData(findCard);
        } catch (Exception e) {
            response.setCode(500);
            response.setResponse("failed");
            response.setMessage("카드 조회 도중 오류가 발생했습니다.");
            response.setData(e.toString());
        }

        return response;
    }

    @PostMapping("/update/{id}")
    public Response update(@PathVariable("id") Long cardId, @RequestBody CreditCardInfo creditCardInfo) {
        Response response = new Response();

        try {
            creditCardService.update(cardId, creditCardInfo);
            response.setCode(200);
            response.setResponse("success");
            response.setMessage("카드 수정에 성공하였습니다.");
            response.setData(creditCardInfo);
        } catch (Exception e) {
            response.setCode(500);
            response.setResponse("failed");
            response.setMessage("카드 수정 도중 오류가 발생했습니다.");
            response.setData(e.toString());
        }

        return response;
    }

    @PostMapping("/delete/{id}")
    public Response delete(@PathVariable("id") Long cardId) {
        Response response = new Response();

        try {
            Long deleteId = creditCardService.delete(cardId);
            response.setCode(200);
            response.setResponse("success");
            response.setMessage("카드 삭제에 성공하였습니다.");
            response.setData(deleteId);
        } catch (Exception e) {
            response.setCode(500);
            response.setResponse("failed");
            response.setMessage("카드 삭제 도중 오류가 발생했습니다.");
            response.setData(e.toString());
        }

        return response;
    }
}
