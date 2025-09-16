package com.example.authtest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
* Response Body
* KEY               TYPE            EXAMPLE
* accessToken       String          FFok65zQFQVcFvH2eJ7SS7SBFlTXt0EZ10L5XXXXXXXX
* refreshToken      String          NWG05CKHAsz4k4d3PB0wQUV9ugGlp0YuibQ4XXXXXXXX
* tokenType         String          Bearer
* expiresIn         int             86400
*/

@Setter
@Getter
public class TokenResponse {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private int expiresIn;


    @Override
    public String toString() {
        return "TokenResponse{"
                + "accessToken='" + accessToken + "'\n" +
                ", refreshToken='" + refreshToken + "'\n" +
                ", tokenType='" + tokenType + "'\n" +
                ", expiresIn=" + expiresIn +
                '}';
    }
}
