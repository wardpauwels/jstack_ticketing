package be.jstack.ticketing.exception;

public class NoProcessDefinitionFoundException extends RuntimeException {
    public NoProcessDefinitionFoundException() {
        super("No process definition found");
    }
}