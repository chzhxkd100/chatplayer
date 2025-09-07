package com.example.authtest.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@RestController
public class UserController {

    @Value("${chzzk.client-id}")
    private String clientId;

    private static final String REDIRECT_URI = "http://localhost:8080/callback";
    private static final String STATE = "czxczx312321"; // 아무거나입력하면된다

    @GetMapping("/user/login")
    public ResponseEntity<Void> loginRedirect() {
        // /user/login 으로 접속하면 OAuth 로 리다이렉트 되게 함
        // Auth 진행 후 http://localhost:8080/callback (앱 등록 시 등록한 Redirect URL 으로 또 리다이렉트 옴)
        URI authUri = UriComponentsBuilder
                .fromUriString("https://chzzk.naver.com/account-interlock")
                .queryParam("clientId", clientId)
                .queryParam("redirectUri", REDIRECT_URI)
                .queryParam("state", STATE)
                .build(StandardCharsets.UTF_8);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(authUri)
                .build();
    }
}
