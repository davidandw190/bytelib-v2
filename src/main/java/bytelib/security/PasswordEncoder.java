package bytelib.security;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncoder {

    private static final int LOG_ROUNDS = 12;

    public static String hashPassword(String password) {
        String salt = BCrypt.gensalt(LOG_ROUNDS);
        return BCrypt.hashpw(password, salt);
    }

    public static boolean verifyPassword(String inputPassword, String hashedPassword) {
        return BCrypt.checkpw(inputPassword, hashedPassword);
    }
}
