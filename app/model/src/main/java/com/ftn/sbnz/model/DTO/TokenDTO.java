package com.ftn.sbnz.model.DTO;

public class TokenDTO {
    private String token;
    private String refreshToken;

    public TokenDTO() {

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}