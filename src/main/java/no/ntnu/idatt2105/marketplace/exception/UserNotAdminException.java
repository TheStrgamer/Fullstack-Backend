package no.ntnu.idatt2105.marketplace.exception;

public class UserNotAdminException extends RuntimeException{
  public UserNotAdminException(String message) {
        super(message);
    }
}
