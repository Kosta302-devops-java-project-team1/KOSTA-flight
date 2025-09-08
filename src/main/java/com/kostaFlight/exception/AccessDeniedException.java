package main.java.com.kostaFlight.exception;

public class AccessDeniedException extends Exception{
    public AccessDeniedException() {
    }

    public AccessDeniedException(String message) {
        super(message);
    }
}
