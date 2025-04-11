package no.ntnu.idatt2105.marketplace.dto.other;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data transfer object representing a JWT token and its expiration timestamp.
 *
 * @author Jonas Reiher
 * @author Erlend Eide Zindel
 * @author Konrad Seime
 * @author Eskild Smestu
 * @version 1.0
 * @since 1.0
 */
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

    /**
     * Constructs a TokenResponseObject with the specified token and expiration timestamp.
     *
     * @param token      the JWT token
     * @param expiration the expiration timestamp in milliseconds since epoch
     */
    public TokenResponseObject(String token, Long expiration) {
        this.token = token;
        this.expiration = expiration;
    }

    /**
     * Gets the JWT token.
     *
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the JWT token.
     *
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Gets the expiration timestamp.
     *
     * @return the expiration timestamp in milliseconds
     */
    public Long getExpiration() {
        return expiration;
    }

    /**
     * Sets the expiration timestamp.
     *
     * @param expiration the expiration timestamp to set
     */
    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }
}