package bytelib.dto;

import java.io.Serializable;

public record RegistrationResponse(
        boolean success,
        String message

) implements Serializable {}
