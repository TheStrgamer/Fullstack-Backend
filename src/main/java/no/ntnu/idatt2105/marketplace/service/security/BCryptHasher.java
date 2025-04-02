package no.ntnu.idatt2105.marketplace.service.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
public class BCryptHasher {

  private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

  private boolean stringEmpty(String password) {
    return password == null || password.isEmpty();
  }

  public String hashPassword(String password) {
    if (stringEmpty(password)) {
      return null;
    }
    return bCryptPasswordEncoder.encode(password);
  }

  public boolean checkPassword(String password, String hash) {
    if (stringEmpty(password) || stringEmpty(hash)) {
      return false;
    }
    return bCryptPasswordEncoder.matches(password, hash);
  }


}
