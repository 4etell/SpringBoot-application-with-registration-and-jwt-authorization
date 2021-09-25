package com.foretell.jwtauthenticationapp.util.validation;

import com.foretell.jwtauthenticationapp.dto.req.RegistrationReqDto;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class RegistrationValidation {

    public static ValidationStatus validateRegistrationReqDto(RegistrationReqDto registrationReqDto) {

        if (!validateUsername(registrationReqDto.getUsername())) {
            log.error("Invalid username");
            return ValidationStatus.INVALID_USERNAME;
        }

        if (!validateEmail(registrationReqDto.getEmail())) {
            log.error("Invalid mail");
            return ValidationStatus.INVALID_MAIL;
        }

        if (!validateName(registrationReqDto.getFirstName())) {
            log.error("Invalid firstName");
            return ValidationStatus.INVALID_FIRSTNAME;
        }

        if (!validateName(registrationReqDto.getLastName())) {
            log.error("Invalid lastName");
            return ValidationStatus.INVALID_LASTNAME;
        }


        return ValidationStatus.SUCCESSFULLY;
    }

    private static Boolean validateEmail(String email) {
        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                        "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    private static boolean validateUsername(String username) {
        final String USERNAME_PATTERN = "^[a-zA-Z][a-zA-Z0-9-_\\.]{1,20}$";

        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
        Matcher matcher = pattern.matcher(username);

        return matcher.matches();
    }

    private static boolean validateName(String name) {
        return name.length() >= 1 && name.length() <= 200;
    }
}
