package com.springboot.blog.dto;

import lombok.Data;


@Data
public class JwtAuthResponse {

    private String accessToken;
    private String tokenType = "Bearer";

    public JwtAuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }

//    public void setAccessToken(String accessToken) {
//        this.accessToken = accessToken;
//    }
//
//    public void setTokenType(String tokenType) {
//        this.tokenType = tokenType;
//    }
//
//    public String getAccessToken() {
//        return accessToken;
//    }
//
//    public String getTokenType() {
//        return tokenType;
//    }
}
