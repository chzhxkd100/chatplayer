package com.example.authtest;//


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
@RestController
public class AuthtestApplication {

    private static final String CLIENT_ID = "bd9b4d44-58ad-4926-92ef-54ea24f2b9cc";
    private static final String CLIENT_SECRET = "AG_HCaJOj_F2SS4_LQXdA_z07SVvZKKmsvi09M_soI8";
    private static final String REDIRECT_URI = "http://localhost:8080/callback";
    private static final String STATE = "czxczx312321"; // 아무거나입력하면된다
    private static final String API_BASE = "https://openapi.chzzk.naver.com";

    public static void main(String[] args) {
        SpringApplication.run(AuthtestApplication.class, args);
    }

    @GetMapping("/login")
    public ResponseEntity<Void> loginRedirect() {

        // /login 으로 접속하면 OAuth 로 리다이렉트 되게 함
        // Auth 진행 후 http://localhost:8080/callback (앱 등록 시 등록한 Redirect URL 으로 또 리다이렉트 옴)
        URI authUri = UriComponentsBuilder
                .fromUriString("https://chzzk.naver.com/account-interlock")
                .queryParam("clientId", CLIENT_ID)
                .queryParam("redirectUri", REDIRECT_URI)
                .queryParam("state", STATE)
                .build(StandardCharsets.UTF_8);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(authUri)
                .build();
    }

    @GetMapping("/callback")
    public String callback( @RequestParam String code, @RequestParam String state) throws UnsupportedEncodingException {
        System.out.println("code: " + code);
        System.out.println("state: " + state);

        RestTemplate restTemplate = new RestTemplate();

        // 인증 요청 응답 파싱
        String body = "grantType=authorization_code" +
                "&clientId=" + URLEncoder.encode(CLIENT_ID, StandardCharsets.UTF_8) +
                "&clientSecret=" + URLEncoder.encode(CLIENT_SECRET, StandardCharsets.UTF_8) +
                "&code=" + URLEncoder.encode(code, StandardCharsets.UTF_8) +
                "&state=" + URLEncoder.encode(state, StandardCharsets.UTF_8);

        // Access Token 발급 요청 헤더
        String jsonBody = String.format(
                "{\"grantType\": \"authorization_code\", \"clientId\": \"%s\", \"clientSecret\": \"%s\", \"code\": \"%s\", \"state\": \"%s\"}",
                CLIENT_ID, CLIENT_SECRET, code, state);

        HttpHeaders headers = new HttpHeaders();
        // headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
        //바디를 JSON 으로 보내야된다!!!!
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

        //Access Token 발급 요청
        ResponseEntity<String> tokenResponse = restTemplate.exchange(
                API_BASE + "/auth/v1/token",
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        /*
        * Response Body
        * KEY               TYPE            EXAMPLE
        * accessToken       String          FFok65zQFQVcFvH2eJ7SS7SBFlTXt0EZ10L5XXXXXXXX
        * refreshToken                      NWG05CKHAsz4k4d3PB0wQUV9ugGlp0YuibQ4XXXXXXXX
        * tokenType                         Bearer 고정
        * expiresIn                         86400
        *
        */

        // 여기에 응답으로 다시 세션API로 요청보내고, 그응답으로 또 얻어서 채팅메시지얻고또 그걸로

        return "토큰 응답: " + tokenResponse.getBody();
    }
}
