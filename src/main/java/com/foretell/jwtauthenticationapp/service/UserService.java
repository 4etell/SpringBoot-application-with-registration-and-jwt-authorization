package com.foretell.jwtauthenticationapp.service;

import com.foretell.jwtauthenticationapp.dto.req.RegistrationReqDto;
import com.foretell.jwtauthenticationapp.exception.UsernameAlreadyExistsException;
import com.foretell.jwtauthenticationapp.model.User;

public interface UserService {

    User register(RegistrationReqDto user) throws UsernameAlreadyExistsException;

    User findByUsername(String username);

}
