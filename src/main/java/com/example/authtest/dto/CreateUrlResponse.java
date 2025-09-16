package com.example.authtest.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUrlResponse {
    @JsonProperty("url")
    private String url;

    @Override
    public String toString(){
        return url;
    }
}
