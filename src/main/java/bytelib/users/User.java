package bytelib.users;


import java.io.Serializable;
import java.math.BigInteger;

public abstract class User implements Serializable {
    protected BigInteger userId;
    protected String password;
    protected String username;
    protected String email;
    protected String phoneNo;

    protected static BigInteger currentIdNumber = BigInteger.ZERO;

    public User(String username, String password, String email, String phoneNo) {
        this.userId = generateUniqueUserId();

        this.password = password;
        this.username = username;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    public boolean checkCredentials(String usernameOrEmail, String password) {

        return (this.email.equalsIgnoreCase(usernameOrEmail) && this.password.equals(password)) ||
                (this.username.equalsIgnoreCase(usernameOrEmail) && this.password.equals(password));
    }

    public BigInteger getUserId() {
        return userId;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    private BigInteger generateUniqueUserId() {
        return User.currentIdNumber.add(BigInteger.ONE);
    }

    public static void setCurrentIdNumber(BigInteger currentIdNumber) {
        User.currentIdNumber = currentIdNumber;
    }









}
