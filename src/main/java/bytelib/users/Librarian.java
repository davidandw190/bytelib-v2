package bytelib.users;

import bytelib.BorrowRequest;

import java.math.BigInteger;

public class Librarian extends User {

    public Librarian(BigInteger userId, String name, String password, String email, String phoneNum) {
        super(userId, name, password, email, phoneNum);
    }

    public void acceptBorrowRequest(BorrowRequest borrowRequest, int borrowPeriodDays) {
        borrowRequest.acceptRequest(borrowPeriodDays);
    }

}
