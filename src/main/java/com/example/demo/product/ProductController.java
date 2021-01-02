package com.example.demo.product;

import com.example.demo.domain.Response;
import com.example.demo.order.OrderService;
import com.example.demo.user.JwtUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final OrderService orderService;
    private final JwtUserDetailsService userService;

    @GetMapping("/buy/{id}")
    public Response buyProduct(@PathVariable("id")Long productId,Principal principal){
        Response response = new Response();
        try {
            Product buyProduct = productService.buy(principal.getName(), productId);
            if(buyProduct==null){
                throw new Exception("잔액부족입니다.");
            }
            response.setResponse("success");
            response.setMessage("구매 처리 완료");
            response.setData(buyProduct);
        } catch (Exception e) {
            response.setResponse("failed");
            response.setMessage("구매 처리하는 도중 오류가 발생했습니다.");
            response.setData(e.toString());
        }
        return response;
    }

    @PostMapping("/save")
    public Response save(@RequestBody ProductDto productDto, Principal principal){

        Response response = new Response();
        try {
            Product savedProduct = productService.addSellingProduct(productDto, principal.getName());
            response.setCode(200);
            response.setResponse("success");
            response.setMessage("상품 등록에 성공하였습니다.");
            ProductDto answerDto = ProductDto.toDto(savedProduct);
            response.setData(answerDto);
        } catch (Exception e) {
            response.setCode(500);
            response.setResponse("failed");
            response.setMessage("상품 등록 도중 오류가 발생했습니다.");
            response.setData(e.toString());
        }
        return response;
    }

    @GetMapping("/products")
    public Response signup(ProductDto infoDto) { // 회원 추가
        Response response = new Response();

        try {
            response.setCode(200);
            response.setResponse("success");
            response.setMessage("상품 리스트를 전체 조회하는 것에 성공하였습니다.");
            response.setData(productService.findAll());
        } catch (Exception e) {
            response.setResponse("failed");
            response.setMessage("상품 리스트를 전체 조회하는 도중 오류가 발생했습니다.");
            response.setData(e.toString());
        }
        return response;
    }

    @GetMapping("/{id}")
    public Response findOne(@PathVariable("id") Long id) {
        Response response = new Response();

        try {
            response.setCode(200);
            Product findProduct = productService.findById(id);
            response.setResponse("success");
            response.setMessage("상품 " + findProduct + " 를 조회하는 것에 성공하였습니다.");
            response.setData(findProduct);
        } catch (Exception e) {
            response.setCode(400);
            response.setResponse("failed");
            response.setMessage("상품을 조회하는 도중 오류가 발생했습니다.");
            response.setData(e.toString());
        }
        return response;
    }

    @PostMapping("/update/{id}")
    public Response update(@PathVariable Long id, @RequestBody ProductDto infoDto) {
        Response response = new Response();

        try {
            response.setCode(200);
            Long updateId = productService.update(id, infoDto);
            Product afterProduct = productService.findById(updateId);
            response.setResponse("success");
            response.setMessage("상품이 갱신되었습니다.");
            response.setData(afterProduct);
        } catch (Exception e) {
            response.setCode(400);
            response.setResponse("failed");
            response.setMessage("상품 업데이트 도중 오류가 발생했습니다.");
            response.setData(e.toString());
        }

        return response;
    }

    @PostMapping("/delete/{id}")
    public Response delete(@PathVariable Long id) {
        Response response = new Response();

        try {
            response.setCode(200);
            Product p = productService.findById(id);
            Long delete_id = productService.delete(id);
            response.setResponse("success");
            response.setMessage("상품 " + p.getName() + " 이(가) 제거되었습니다. " + (delete_id == p.getId()));
            response.setData(p);
        } catch (Exception e) {
            response.setCode(400);
            response.setResponse("failed");
            response.setMessage("상품 제거 도중 오류가 발생했습니다.");
            response.setData(e.toString());
        }

        return response;
    }
}