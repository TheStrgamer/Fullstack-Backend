package no.ntnu.idatt2105.marketplace.service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import no.ntnu.idatt2105.marketplace.model.user.User;
public class JWT_token {

  private static final String SECRET_KEY = "MonkeyKeyForJWTThatWillNotMakeMeAsStrongAsLimaButItWillEncryptShit"; //would put in .env but lazy
  private static final long EXPIRATION_TIME = 5 * 60 * 1000;

  private String generateJwtToken(User user) {
    return Jwts.builder()
        .setSubject(user.getEmail())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
        .compact();
  }

  public boolean validateJwtToken(String token) {
    try {
      Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
      return true;
    } catch (ExpiredJwtException e) {
      System.out.println("Token expired");
    } catch (UnsupportedJwtException | MalformedJwtException e) {
      System.out.println("Invalid token");
    }
    return false;
  }

  public String extractUsernameFromJwt(String token) {
    try {
      Claims claims = Jwts.parser()
          .setSigningKey(SECRET_KEY)
          .parseClaimsJws(token)
          .getBody();
      return claims.getSubject();
    } catch (Exception e) {
      return null;
    }
  }
  public User getUserByToken(String token) {
    String username = extractUsernameFromJwt(token);
    return null; //TODO: implement this
  }
}
