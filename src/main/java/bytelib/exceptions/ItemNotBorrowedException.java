package bytelib.exceptions;

public class ItemNotBorrowedException extends Exception {
    public ItemNotBorrowedException(String message) {
        super(message);
    }

    public ItemNotBorrowedException(String message, Throwable cause) {
        super(message, cause);
    }
}
