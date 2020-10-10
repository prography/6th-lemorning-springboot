package com.example.demo.shop;

import com.example.demo.domain.Response;
import com.example.demo.user.JwtUserDetailsService;
import com.example.demo.user.User;
import com.example.demo.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    private final JwtUserDetailsService userService;

    @PostMapping("/save")
    public Response save(@RequestBody ProductDto productDto, Principal principal){
        Response response = new Response();
        try {
            User findUser = userService.findByEmail(principal.getName());
            Long savedId = productService.save(productDto,findUser);
            Product findProduct = productService.findById(savedId);
            response.setCode(200);
            response.setResponse("success");
            response.setMessage("상품 등록에 성공하였습니다.");

            ProductDto answerDto = ProductDto.toDto(findProduct, findUser.getId());
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
    public Response findOne(@PathVariable("id")Long id){
        Response response = new Response();

        try {
            response.setCode(200);
            Product findProduct = productService.findById(id);
            response.setResponse("success");
            response.setMessage("상품 "+findProduct+" 를 조회하는 것에 성공하였습니다.");
            response.setData(findProduct);
        } catch (Exception e) {
            response.setCode(400);
            response.setResponse("failed");
            response.setMessage("상품을 조회하는 도중 오류가 발생했습니다.");
            response.setData(e.toString());
        }
        return response;
    }

}