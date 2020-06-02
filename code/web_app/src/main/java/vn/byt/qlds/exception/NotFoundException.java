package vn.byt.qlds.exception;

@SuppressWarnings("serial")
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}