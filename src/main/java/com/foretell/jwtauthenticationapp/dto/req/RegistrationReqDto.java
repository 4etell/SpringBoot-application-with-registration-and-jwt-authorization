package com.foretell.jwtauthenticationapp.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationReqDto {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
