package no.ntnu.idatt2105.marketplace.dto.other;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link TokenResponseObject}.
 */
class TokenResponseObjectTest {

  /**
   * Test constructor and getter methods.
   */
  @Test
  void constructor_shouldSetFieldsCorrectly() {
    String token = "abc.def.ghi";
    long expiration = 1713541823000L;

    TokenResponseObject response = new TokenResponseObject(token, expiration);

    assertEquals(token, response.getToken());
    assertEquals(expiration, response.getExpiration());
  }

  /**
   * Test setting and retrieving values using setters and getters.
   */
  @Test
  void settersAndGetters_shouldModifyAndReturnCorrectValues() {
    TokenResponseObject response = new TokenResponseObject("", 0L);

    String newToken = "new.jwt.token";
    long newExpiration = 1714000000000L;

    response.setToken(newToken);
    response.setExpiration(newExpiration);

    assertEquals(newToken, response.getToken());
    assertEquals(newExpiration, response.getExpiration());
  }
}
