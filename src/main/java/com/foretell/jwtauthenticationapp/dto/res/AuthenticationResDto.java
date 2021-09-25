package com.foretell.jwtauthenticationapp.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResDto {
    private String username;
    private String token;
}
