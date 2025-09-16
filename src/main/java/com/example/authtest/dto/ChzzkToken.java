package com.example.authtest.dto;

import lombok.Value;

@Value
public class ChzzkToken {
    private String accessToken;
    private String refreshToken;
    private String tokenType;  // Bearer (maybe fixed value)
    private int expiresln; // 86400
}
