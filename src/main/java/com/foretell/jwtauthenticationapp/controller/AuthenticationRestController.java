package com.foretell.jwtauthenticationapp.controller;

import com.foretell.jwtauthenticationapp.dto.req.AuthenticationReqDto;
import com.foretell.jwtauthenticationapp.dto.req.RegistrationReqDto;
import com.foretell.jwtauthenticationapp.dto.res.AuthenticationResDto;
import com.foretell.jwtauthenticationapp.model.User;
import com.foretell.jwtauthenticationapp.util.jwt.JwtProvider;
import com.foretell.jwtauthenticationapp.util.validation.ValidationStatus;
import com.foretell.jwtauthenticationapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthenticationRestController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    public AuthenticationRestController(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("login")
    public ResponseEntity<AuthenticationResDto> login(@RequestBody AuthenticationReqDto authenticationReqDto) {

        try {
            String username = authenticationReqDto.getUsername();

            User user = userService.findByUsername(username);

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            String token = jwtProvider.generateToken(username);
            AuthenticationResDto authenticationResDto = new AuthenticationResDto(username, token);

            return ResponseEntity.ok(authenticationResDto);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegistrationReqDto registrationReqDto) {

        ValidationStatus validationStatus = userService.register(registrationReqDto);

        switch (validationStatus) {
            case SUCCESSFULLY:
                return ResponseEntity.ok("Successful registration");
            case INVALID_USERNAME:
                return ResponseEntity.badRequest().body("Invalid username");
            case USERNAME_ALREADY_EXISTS:
                return ResponseEntity.badRequest().body("User with username: " +
                        registrationReqDto.getUsername() + " already exists");
            case INVALID_MAIL:
                return ResponseEntity.badRequest().body("Invalid mail");
            case INVALID_FIRSTNAME:
                return ResponseEntity.badRequest().body("Invalid firstName");
            case INVALID_LASTNAME:
                return ResponseEntity.badRequest().body("Invalid lastName");
            default:
                return ResponseEntity.badRequest().body("Something wrong in request");
        }
    }

}
