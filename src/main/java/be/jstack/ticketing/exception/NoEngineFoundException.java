package be.jstack.ticketing.exception;

public class NoEngineFoundException extends RuntimeException {
    public NoEngineFoundException() {
        super("No engine found");
    }
}