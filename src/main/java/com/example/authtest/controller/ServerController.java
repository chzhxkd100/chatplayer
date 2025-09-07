package com.example.authtest.controller;

import com.example.authtest.dto.TokenResponse;
import com.example.authtest.service.ChzzkApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController {

    @Autowired // 자동으로 객체 생성 (어노테이션들 붙은거)
    private ChzzkApiService chzzkApiService;

    @GetMapping("/callback")
    public String callback(@RequestParam String code, @RequestParam String state) {
        System.out.println("code: " + code);
        System.out.println("state: " + state);

        // Exchange the authorization code for an access token by calling the service
        TokenResponse tokenResponse = chzzkApiService.getAccessToken(code, state);

        // 여기에 응답으로 다시 세션API로 요청보내고, 그응답으로 또 얻어서 채팅메시지얻고또 그걸로

        // Now you can use the access token to make further API calls
        // For example, to get user profile or connect to chat

        return "Access Token: " + tokenResponse.getAccessToken();
    }
}
