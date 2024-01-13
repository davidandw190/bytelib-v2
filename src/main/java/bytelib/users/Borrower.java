package bytelib.users;

import bytelib.BorrowRequest;
import bytelib.Library;
import bytelib.items.Borrowable;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Borrower extends User {
    protected Map<BigInteger, Borrowable> borrowedItems;

    public Borrower(String name, String password, String email, String phoneNum) {
        super(name, password, email, phoneNum);
        this.borrowedItems = new HashMap<>();
    }

    public Borrower(BigInteger userId, String name, String password, String email, String phoneNum) {
        super(userId, name, password, email, phoneNum);
        this.borrowedItems = new HashMap<>();
    }

    public List<BorrowRequest> getBorrowRequests(Library library) {
//        List<BorrowRequest> userBorrowRequests = library.getUserBorrowRequests(this);
//        if (userBorrowRequests.isEmpty()) {
//            System.out.println("No borrow requests present.");
//            return new ArrayList<>();
//        }
        return new ArrayList<>();
    }
}
