package no.ntnu.idatt2105.marketplace.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }

}
