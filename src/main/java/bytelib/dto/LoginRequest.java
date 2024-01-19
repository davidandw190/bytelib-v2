package bytelib.dto;

import java.io.Serializable;

public record LoginRequest(
        String usernameOrEmail,
        String password
) implements Serializable { }
