package com.example.securityrest3.security.model.token;

public final class AccessJwtToken implements JwtToken {

    private final String token;

    public AccessJwtToken(final String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

}
