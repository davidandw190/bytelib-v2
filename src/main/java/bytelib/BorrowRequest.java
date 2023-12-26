package bytelib;

import bytelib.enums.BorrowRequestStatus;
import bytelib.items.Borrowable;
import bytelib.users.Borrower;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BorrowRequest implements Serializable {
    private Borrower borrower;
    private Borrowable borrowable;
    private BorrowRequestStatus status;
    private Date requestDate;
    private Date borrowDate;
    private Date returnDate;
    private Date returnDeadline;

    public BorrowRequest(Borrower borrower, Borrowable borrowable) {
        this.borrower = borrower;
        this.borrowable = borrowable;
        this.requestDate = new Date();
        this.status = BorrowRequestStatus.PENDING_APPROVAL;
    }


    public void acceptRequest(int borrowPeriodDays) {
        this.status = BorrowRequestStatus.APPROVED;
        borrowable.setStatusBorrowed();
        this.borrowDate = new Date();
        this.returnDeadline = new Date();
        this.returnDeadline = calculateReturnDeadline(borrowPeriodDays);
    }

    public void rejectRequest() {
        this.status = BorrowRequestStatus.REJECTED;
    }

    public void returnItem() {
        if (this.status == BorrowRequestStatus.APPROVED  || checkIfOverdue()) {
            this.borrowable.setStatusAvailable();
            this.returnDate = new Date();
        } else {
            throw new IllegalStateException("Cannot return the book. Invalid borrow request or the book is not borrowed.");
        }
    }

    private Date calculateReturnDeadline(int borrowPeriodDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, borrowPeriodDays);

        return calendar.getTime();
    }

    public boolean checkIfOverdue() {
        if (this.status == BorrowRequestStatus.OVERDUE) {
            return true;
        } else if ( this.status == BorrowRequestStatus.APPROVED && new Date().after(returnDeadline)) {
            this.status = BorrowRequestStatus.OVERDUE;
            return true;
        } else {
            return false;
        }
    }

    public double computeReturnFee(double perDayFine, int returnDeadline) {
        if (checkIfOverdue()) {
            int overdueDays = calculateOverdueDays(returnDeadline);
            return (overdueDays > 0) ? (overdueDays * perDayFine) : 0;
        }
        return 0.0; // No overdue, return fee is 0
    }

    public int calculateOverdueDays(int allowedReturnDays) {
        int borrowedDays = getBorrowedDays();
        int overdueDays = borrowedDays - allowedReturnDays;
        return Math.max(overdueDays, 0); // Ensure a non-negative value
    }

    private int getBorrowedDays() {
        if (this.status == BorrowRequestStatus.APPROVED) {
            long diffInMillies = Math.abs(new Date().getTime() - borrowDate.getTime());
            return (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        }
        return 0; // If the book is not borrowed or request is not approved
    }

    public Borrower getBorrower() {
        return borrower;
    }

    public void setBorrower(Borrower borrower) {
        this.borrower = borrower;
    }

    public Borrowable getBorrowable() {
        return borrowable;
    }

    public void setBorrowable(Borrowable borrowable) {
        this.borrowable = borrowable;
    }

    public BorrowRequestStatus getStatus() {
        return status;
    }

    public void setStatus(BorrowRequestStatus status) {
        this.status = status;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDeadline() {
        return returnDeadline;
    }

    public void setReturnDeadline(Date returnDeadline) {
        this.returnDeadline = returnDeadline;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
