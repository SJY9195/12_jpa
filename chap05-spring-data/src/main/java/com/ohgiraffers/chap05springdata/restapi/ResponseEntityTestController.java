package com.ohgiraffers.chap05springdata.restapi;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.nio.charset.Charset;
import java.util.*;

@RestController
@RequestMapping("/entity")
public class ResponseEntityTestController {

    /*
    * ResponseEntity 란?
    * 결과 데이터와 http 상태 코드를 직접 제어할 수 있는 클래스이다.
    * HttpStatus, HttpHeaders, HttpBody 를 포함한다.
    * */

    private List<UserDTO> users;

    public ResponseEntityTestController(){
        users = new ArrayList<>();
        users.add(new UserDTO(1, "user01", "pass01", "홍길동", new Date()));
        users.add(new UserDTO(2, "user02", "pass02", "유관순", new Date()));
        users.add(new UserDTO(3, "user03", "pass03", "이순신", new Date()));
    } //users는 클래스가 Bean으로 시작될 때 생성된다.

    // 모든 사용자를 조회하는 api 엔드포인트 메소드
    @GetMapping("/users")
    public ResponseEntity findAllUsers(){

        // http 헤더 객체 생성 - 필수 아님
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json"
        , Charset.forName("UTF-8")));  //json 말고 다른걸로 받아야 한다면 이걸 써줘야 한다! 지금은 기본세팅이 되어있어서 이렇게 쓸 필요없다!

        // 응답에 포함할 데이터 맵을 생성
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("users", users);  //List에 담고 Map으로 뿌려준다.

        return new ResponseEntity(responseMap, headers , HttpStatus.OK);
    }

    @GetMapping("/users/{userNo}")   //@PathVariable {userNo}를 받아오는 역할! useparams와 비슷한 역할이다.
    public ResponseEntity findUserByNo(@PathVariable int userNo){

        // http 헤더 객체 생성 - 필수 아님
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json"
                , Charset.forName("UTF-8")));

        UserDTO foundUser = null;
        for (UserDTO user:users){
            if(user.getNo() == userNo){
                foundUser = user;
                break;
            }
        }

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("user", foundUser);
        return new ResponseEntity(responseMap, headers, HttpStatus.OK);
    }
}
