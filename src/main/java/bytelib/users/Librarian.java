package bytelib.users;

import bytelib.BorrowRequest;

public class Librarian extends User {
    public Librarian(String name, String password, String email, String phoneNum) {
        super(name, password, email, phoneNum);
    }

    public void acceptBorrowRequest(BorrowRequest borrowRequest, int borrowPeriodDays) {
        borrowRequest.acceptRequest(borrowPeriodDays);
    }

}
