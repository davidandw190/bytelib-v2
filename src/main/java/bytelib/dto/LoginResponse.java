package bytelib.dto;

import bytelib.users.User;

import java.io.Serializable;

public record LoginResponse(
        boolean success,
        User authenticatedUser
) implements Serializable { }