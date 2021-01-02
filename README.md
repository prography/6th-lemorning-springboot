1. [post] http://localhost:8080/signup   
```
{
    "email":"test@naver.com",
    "password":"123",
    "auth":"ROLE_USER",
    "nickname":"test",
    "birthday":"2020-12-12",
    "gender":"MALE",
    "profileImageUrl":"/sfdsdf"
}
```
2. [post] http://localhost:8080/authenticate   
```
{
    "username":"test@naver.com",
    "password":"123"
}
```
3. [post] http://localhost:8080/product/save   
- auth : Bearer token
- product 2-3개정도 생성하자.
```
{
    "name":"product1",
    "categoryName":"humor",
    "imageUrl":"https://ventulus95.gitbook.io/lemorning/shop_api/upload-api",
    "alarmUrl":"https://ventulus95.gitbook.io/lemorning/shop_api/upload-api",
    "price":100
}
```
4. [post] http://localhost:8080/signup   
```
{
    "email":"test2@naver.com",
    "password":"123",
    "auth":"ROLE_USER"
}
```
5. [post] http://localhost:8080/authenticate   
```
{
    "username":"test2@naver.com",
    "password":"123"
}
```
6. [post] http://localhost:8080/product/save  
- 2번째 생성 한 다른 유저 토큰으로 물품 생산하자. 
- auth : Bearer token
- product 2-3개정도 생성하자.
```
{
    "name":"product4",
    "categoryName":"humor",
    "imageUrl":"https://ventulus95.gitbook.io/lemorning/shop_api/upload-api",
    "alarmUrl":"https://ventulus95.gitbook.io/lemorning/shop_api/upload-api",
    "price":100
}
```
7. [get] http://localhost:8080/user/mypage/charge/200000  
- 현재 인증되어있는 2번 유저가 돈이 충전된다.
8. [get] http://localhost:8080/user/mypage/charge/200000  
- 현재 인증되어있는 2번 유저가 돈이 충전된다.

9. [POST] http://localhost:8080/card/save
- 카드 정보를 입력하면, 저장 후 카드 닉네임, 이메일을 반환해줍니다.
- request
```
{
    "creditCardBank":"카카오뱅크",
    "cardNickname":"카뱅테스트",
    "cardNum":"1234-2345-3456-4567",
    "expireYear":23,
    "expireMonth":12,
    "birth":"2020-12-12",
    "simplePassword":"1234"
}
```
- response
```
{
    "code": 200,
    "response": "success",
    "message": "카드 등록에 성공하였습니다.",
    "data": {
        "cardNickname": "카뱅테스트",
        "email": "test@naver.com"
    }
}
```