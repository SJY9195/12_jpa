package com.ohgiraffers.chap06securityjwt.user.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class AuthAdminController {

    @GetMapping("/admin")
    public String admin(){
        return "admin hello";
    }    /// 포스트맨에서 실행시 로그인시 Header에 Authorization 에 해당하는 토큰 값을  admin 요청시 Header에 넣어줘야 된다!!!
}
