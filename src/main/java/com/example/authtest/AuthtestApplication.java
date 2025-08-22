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
    private static final String STATE = "czxczx312321";
    private static final String API_BASE = "https://openapi.chzzk.naver.com";

    public static void main(String[] args) {
        SpringApplication.run(AuthtestApplication.class, args);
    }

    @GetMapping("/login")
    public ResponseEntity<Void> loginRedirect() {
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
    public String callback(
            @RequestParam String code,
            @RequestParam String state
    ) throws UnsupportedEncodingException {
        System.out.println("code: " + code);
        System.out.println("state: " + state);

        RestTemplate restTemplate = new RestTemplate();

        String body = "grantType=authorization_code" +
                "&clientId=" + URLEncoder.encode(CLIENT_ID, StandardCharsets.UTF_8) +
                "&clientSecret=" + URLEncoder.encode(CLIENT_SECRET, StandardCharsets.UTF_8) +
                "&code=" + URLEncoder.encode(code, StandardCharsets.UTF_8) +
                "&state=" + URLEncoder.encode(state, StandardCharsets.UTF_8);

        String jsonBody = String.format(
                "{\"grantType\": \"authorization_code\", \"clientId\": \"%s\", \"clientSecret\": \"%s\", \"code\": \"%s\", \"state\": \"%s\"}",
                CLIENT_ID, CLIENT_SECRET, code, state);

        HttpHeaders headers = new HttpHeaders();
        // headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
        //바디를 JSON 으로 보내야된다!!!!
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

        ResponseEntity<String> tokenResponse = restTemplate.exchange(
                API_BASE + "/auth/v1/token",
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        return "토큰 응답: " + tokenResponse.getBody();
    }
}
