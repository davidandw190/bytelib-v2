package bytelib.exceptions;

public class ItemCurrentlyBorrowedException extends Exception {
    public ItemCurrentlyBorrowedException(String message) {
        super(message);
    }

    public ItemCurrentlyBorrowedException(String message, Throwable cause) {
        super(message, cause);
    }


}
