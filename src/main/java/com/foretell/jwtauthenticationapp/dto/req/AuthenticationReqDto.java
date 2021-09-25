package com.foretell.jwtauthenticationapp.dto.req;

import lombok.Data;

@Data
public class AuthenticationReqDto {
    private String username;
    private String password;
}
