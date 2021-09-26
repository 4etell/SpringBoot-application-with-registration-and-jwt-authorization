package com.foretell.jwtauthenticationapp.service.impl;

import com.foretell.jwtauthenticationapp.dto.req.RegistrationReqDto;
import com.foretell.jwtauthenticationapp.exception.UsernameAlreadyExistsException;
import com.foretell.jwtauthenticationapp.model.Role;
import com.foretell.jwtauthenticationapp.model.User;
import com.foretell.jwtauthenticationapp.repository.RoleRepo;
import com.foretell.jwtauthenticationapp.repository.UserRepo;
import com.foretell.jwtauthenticationapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo, BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(RegistrationReqDto registrationReqDto) throws UsernameAlreadyExistsException {

        String usernameFromDto = registrationReqDto.getUsername();
        String firstNameFromDto = registrationReqDto.getFirstName();
        String lastNameFromDto = registrationReqDto.getLastName();
        String emailFromDto = registrationReqDto.getEmail();
        String passwordFromDto = registrationReqDto.getPassword();

        if (userRepo.findByUsername(usernameFromDto) == null) {

            Role roleUser = roleRepo.findByName("ROLE_USER");
            List<Role> userRoles = new ArrayList<>();
            userRoles.add(roleUser);

            Date nowDate = new Date();

            User registeredUser = new User(
                    nowDate,
                    nowDate,
                    usernameFromDto,
                    firstNameFromDto,
                    lastNameFromDto,
                    emailFromDto,
                    passwordEncoder.encode(passwordFromDto),
                    userRoles);

            userRepo.save(registeredUser);
            log.info("IN register - user: {} successfully registered", registeredUser);

            return registeredUser;
        } else {
            log.error("User with username: " + usernameFromDto + " already exists");
            throw new UsernameAlreadyExistsException("User with username: " +
                    usernameFromDto + " already exists");

        }
    }

    @Override
    public User findByUsername(String username) {
        User result = userRepo.findByUsername(username);
        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }
}
