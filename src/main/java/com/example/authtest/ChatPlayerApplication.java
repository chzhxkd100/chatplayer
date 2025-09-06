package com.example.authtest;

import com.example.authtest.dto.TokenResponse;
import com.example.authtest.service.ChzzkApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
@RestController
public class ChatPlayerApplication {

    @Value("${chzzk.client-id}")
    private String clientId;

    private static final String REDIRECT_URI = "http://localhost:8080/callback";
    private static final String STATE = "czxczx312321"; // 아무거나입력하면된다

    @Autowired
    private ChzzkApiService chzzkApiService;

    public static void main(String[] args) {
        SpringApplication.run(ChatPlayerApplication.class, args);
    }

    @GetMapping("/login")
    public ResponseEntity<Void> loginRedirect() {
        // /login 으로 접속하면 OAuth 로 리다이렉트 되게 함
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
