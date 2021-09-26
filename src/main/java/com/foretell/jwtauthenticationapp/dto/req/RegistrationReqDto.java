package com.foretell.jwtauthenticationapp.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class RegistrationReqDto {

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 1, message = "Min size of username: 1")
    @Size(max = 30, message = "Max size of username: 30")
    private String username;

    @NotBlank(message = "Firstname cannot be blank")
    @Size(min = 1, message = "Min size of firstname: 1")
    @Size(max = 200, message = "Max size of firstname: 200")
    private String firstName;

    @NotBlank(message = "Lastname cannot be blank")
    @Size(min = 1, message = "Min size of lastname: 1")
    @Size(max = 200, message = "Max size of lastname: 200")
    private String lastName;

    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Min size of password: 6")
    private String password;
}
