package no.ntnu.idatt2105.marketplace.responseobjects;

public class TokenResponseObject {
    private String token;
    private Long expiration;

    public TokenResponseObject(String token, Long expiration) {
        this.token = token;
        this.expiration = expiration;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public Long getExpiration() {
        return expiration;
    }
    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }
}
