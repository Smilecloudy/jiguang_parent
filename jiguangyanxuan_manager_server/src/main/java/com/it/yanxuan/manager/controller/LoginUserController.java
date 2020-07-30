package com.it.yanxuan.manager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @auther: 曹云博
 * @create: 2020-06-2020/6/30 20:44
 */
@RestController
@RequestMapping("/loginUser")
public class LoginUserController {

    @GetMapping
    public ResponseEntity<Map> getLoginUserName() {
        //从springSecurity中获取登陆的用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        HashMap<String, String> result = new HashMap<>();
        result.put("username", username);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
