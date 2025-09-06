package com.example.authtest.service;

import com.example.authtest.dto.TokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/*
*  Access Token 필요시
*
*  Request Header :
*
*  KEY              TYPE        Example
*  Authorization    String      Bearer FFok65zQFQVcFvH2eJ7SS7SBFlTXt0EZ10L5XXXXXXXX
*  Content-Type     String      application/json
*
*/
@Service
public class ChzzkApiService {

    @Value("${chzzk.client-id}")
    private String clientId;

    @Value("${chzzk.client-secret}")
    private String clientSecret;

    private static final String API_BASE = "https://openapi.chzzk.naver.com";

    private final RestTemplate restTemplate = new RestTemplate();

    public TokenResponse getAccessToken(String code, String state) {
        String url = API_BASE + "/auth/v1/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //바디를 JSON 으로 보내야된다!!!!
        Map<String, String> body = new HashMap<>();
        body.put("grant_type", "authorization_code");
        body.put("client_id", clientId);
        body.put("client_secret", clientSecret);
        body.put("code", code);
        body.put("state", state);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<TokenResponse> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                TokenResponse.class
        );

        return responseEntity.getBody();
    }
}
