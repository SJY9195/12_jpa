package com.ohgiraffers.chap05springdata.restapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/*
* @Controller 와 @ResponseBody 어노테이션을 합친 어노테이션이다.
* 클래스 레벨에 작성하며 해당 클래스 내 모든 핸들러 메소드에 @ResponseBody 어노테이션을  cf)@ResponseBody 는 return시 viewresolver가 아니라 바로 HTTP에 응답을 보내준다! @RestController는 컨트롤러와 리스폰스바디를 합친기능이다!
* 묵시적으로 적용한다.
* */

// cf) 리액트의 fetch 요청대신 쓸 수 있다!
@RestController
@RequestMapping("/response")
public class ResponseRestController {

    @GetMapping("/hello")
    public String helloWorld(){
        return "Hello World!";
    }
    @GetMapping("/random")
    public int getRandomNumber(){
        return (int)(Math.random()*10)+1;
    }

    //object 응답
    @GetMapping("/message")
    public Message getMessage(){
        return new Message(200, "성공적인 응답입니다.");
    }

    // List 응답
    @GetMapping("list")
    public List<Message> getList(){
        List<Message> messageList = new ArrayList<>();
        messageList.add(new Message(200, "정상응답"));
        messageList.add(new Message(404, "페이지를 찾을 수 없음"));
        messageList.add(new Message(500, "개발자의 잘못"));
        return messageList;
    }

}
