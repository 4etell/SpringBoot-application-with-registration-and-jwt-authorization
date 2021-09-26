package com.foretell.jwtauthenticationapp.controller;

import com.foretell.jwtauthenticationapp.dto.req.AuthenticationReqDto;
import com.foretell.jwtauthenticationapp.dto.req.RegistrationReqDto;
import com.foretell.jwtauthenticationapp.dto.res.AuthenticationResDto;
import com.foretell.jwtauthenticationapp.exception.UsernameAlreadyExistsException;
import com.foretell.jwtauthenticationapp.model.User;
import com.foretell.jwtauthenticationapp.service.UserService;
import com.foretell.jwtauthenticationapp.util.jwt.JwtProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthenticationRestController {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    public AuthenticationRestController(UserService userService, JwtProvider jwtProvider,
                                        AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("login")
    public ResponseEntity<AuthenticationResDto> login(@RequestBody @Valid AuthenticationReqDto authenticationReqDto) {

        try {
            String username = authenticationReqDto.getUsername();

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,
                    authenticationReqDto.getPassword()));

            String token = jwtProvider.generateToken(username);
            AuthenticationResDto authenticationResDto = new AuthenticationResDto(username, token);

            return ResponseEntity.ok(authenticationResDto);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody @Valid RegistrationReqDto registrationReqDto) {
        try {
            String registeredUsername = userService.register(registrationReqDto).getUsername();

            String token = jwtProvider.generateToken(registeredUsername);
            AuthenticationResDto authenticationResDto = new AuthenticationResDto(registeredUsername, token);

            return ResponseEntity.ok(authenticationResDto);
        } catch (UsernameAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
