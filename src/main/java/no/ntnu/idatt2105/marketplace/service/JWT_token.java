package no.ntnu.idatt2105.marketplace.service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.security.Keys;
import no.ntnu.idatt2105.marketplace.model.user.User;

public class JWT_token {

  private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
  private static final long EXPIRATION_TIME = 5 * 60 * 1000;

  public String generateJwtToken(User user) {
    return Jwts.builder()
        .setSubject(user.getEmail())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(key)
        .compact();
  }

  public boolean validateJwtToken(String token) {
    try {
      Jwts.parser().setSigningKey(key).parseClaimsJws(token);
      return true;
    } catch (ExpiredJwtException e) {
      System.out.println("Token expired");
    } catch (UnsupportedJwtException | MalformedJwtException e) {
      System.out.println("Invalid token");
    }
    return false;
  }

  public String extractEmailFromJwt(String token) {
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
    String email = extractEmailFromJwt(token);
    return null; //TODO: implement this
  }
}
