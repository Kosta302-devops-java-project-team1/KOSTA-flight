package main.java.com.kostaFlight.exception;

public class InsufficientBalanceException extends Exception{
    public InsufficientBalanceException() {
    }

    public InsufficientBalanceException(String message) {
        super(message);
    }
}
