package bytelib.exceptions;

public class ItemNotAvailableForBorrowException extends Exception {

    public ItemNotAvailableForBorrowException(String message) {
        super(message);
    }

    public ItemNotAvailableForBorrowException(String message, Throwable cause) {
        super(message, cause);
    }
}
