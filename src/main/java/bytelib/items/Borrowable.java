package bytelib.items;

import bytelib.BorrowRequest;
import bytelib.users.Borrower;


public interface Borrowable {
    BorrowRequest initiateBorrowRequest(Borrower borrower);
    void setStatusBorrowed();
    void setStatusAvailable();
    boolean isAvailable();
}
