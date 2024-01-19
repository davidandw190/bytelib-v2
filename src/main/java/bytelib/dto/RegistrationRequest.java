package bytelib.dto;

import java.io.Serializable;

public record RegistrationRequest(
        String username,
        String email,
        String phone,
        String password,
        String accountType

) implements Serializable {}