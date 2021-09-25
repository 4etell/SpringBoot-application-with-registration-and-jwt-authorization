package com.foretell.jwtauthenticationapp.service;

import com.foretell.jwtauthenticationapp.dto.req.RegistrationReqDto;
import com.foretell.jwtauthenticationapp.model.User;
import com.foretell.jwtauthenticationapp.util.validation.ValidationStatus;

public interface UserService {

    ValidationStatus register(RegistrationReqDto user);

    User findByUsername(String username);

}
