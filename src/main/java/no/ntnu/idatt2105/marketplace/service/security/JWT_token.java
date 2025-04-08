package no.ntnu.idatt2105.marketplace.service.security;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.security.Keys;
import no.ntnu.idatt2105.marketplace.exception.TokenExpiredException;
import no.ntnu.idatt2105.marketplace.model.user.User;
import no.ntnu.idatt2105.marketplace.responseobjects.TokenResponseObject;
import no.ntnu.idatt2105.marketplace.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JWT_token {

  @Autowired
  private UserRepo userRepo;
  private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
  private static final long EXPIRATION_TIME = 120 * 60 * 1000;

  public TokenResponseObject generateJwtToken(User user) {
    Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
    String token = Jwts.builder()
            .setSubject(user.getIdAsString())
            .setIssuedAt(new Date())
            .setExpiration(expirationDate)
            .signWith(key)
            .compact();
    return new TokenResponseObject(token, expirationDate.getTime());
  }


  /**
   * Validates the JWT token and checks if it has expired.
   *
   * @param token the JWT token to validate
   * @throws TokenExpiredException if the token has expired
   * @throws IllegalArgumentException if the token is invalid or empty
   */
  public void validateJwtToken(String token) {
    try {
      Jwts.parser().setSigningKey(key).parseClaimsJws(token);
    } catch (ExpiredJwtException e) {
      throw new TokenExpiredException("Token has expired");
    } catch (UnsupportedJwtException | MalformedJwtException e) {
      throw new IllegalArgumentException("Token is invalid");
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Token is empty");
    }
  }


  public String extractIdFromJwt(String token) {
    try {
      Claims claims = Jwts.parser()
          .setSigningKey(key)
          .parseClaimsJws(token)
          .getBody();
      return claims.getSubject();
    } catch (Exception e) {
      return null;
    }
  }
  public User getUserByToken(String token) {
    String id = extractIdFromJwt(token);
    if (id == null) {
      return null;
    }
    return userRepo.findById(Integer.parseInt(id)).orElse(null);
  }
}
