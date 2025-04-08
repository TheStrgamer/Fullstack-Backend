package no.ntnu.idatt2105.marketplace.dto.other;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response object containing a JWT token and its expiration timestamp")
public class TokenResponseObject {

    @Schema(
            description = "The JWT token used for authenticated requests",
            example = "eyJhbGciOiJIUzI1NiIsI.nR5cCI6IkpXVCJ9..."
    )
    private String token;

    @Schema(
            description = "Expiration timestamp in milliseconds since epoch (UTC)",
            example = "1713541823000"
    )
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
