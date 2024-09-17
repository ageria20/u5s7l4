package ageria.u5s7l2.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(long id) {
        super("Element with id: " + id + " NOT FOUND");
    }

    public NotFoundException(String msg) {
        super(msg);
    }
}
