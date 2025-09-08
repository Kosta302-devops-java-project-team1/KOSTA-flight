package main.java.com.kostaFlight.exception;

public class EmailDuplicateException extends Exception{
    public EmailDuplicateException() {
    }

    public EmailDuplicateException(String message) {
        super(message);
    }
}
