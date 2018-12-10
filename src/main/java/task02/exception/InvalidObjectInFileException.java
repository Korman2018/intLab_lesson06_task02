package task02.exception;

public class InvalidObjectInFileException extends RuntimeException {
    public InvalidObjectInFileException(String message) {
        super(message);
    }
}
