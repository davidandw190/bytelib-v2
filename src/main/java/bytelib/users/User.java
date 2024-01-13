package bytelib.users;


import java.io.Serializable;
import java.math.BigInteger;

public abstract class User implements Serializable {
    protected BigInteger userId;
    protected String hashedPassword;
    protected String username;
    protected String email;
    protected String phoneNo;

    public User(BigInteger userId, String username, String hashedPassword, String email, String phoneNo) {
        this.userId = userId;
        this.hashedPassword = hashedPassword;
        this.username = username;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    public User( String username, String hashedPassword, String email, String phoneNo) {
        this.hashedPassword = hashedPassword;
        this.username = username;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public String getHashedPassword() {
        return this.hashedPassword;
    }

    public void setPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
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

}
