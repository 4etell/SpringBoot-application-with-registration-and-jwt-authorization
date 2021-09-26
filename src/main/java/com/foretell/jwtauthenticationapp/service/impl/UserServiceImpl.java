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

        if (userRepo.findByUsername(usernameFromDto) == null) {

            User registeredUser = convertRegistrationReqDtoToUser(registrationReqDto);

            Role roleUser = roleRepo.findByName("ROLE_USER");
            List<Role> userRoles = new ArrayList<>();
            userRoles.add(roleUser);

            registeredUser.setRoles(userRoles);

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

    private User convertRegistrationReqDtoToUser(RegistrationReqDto registrationReqDto) {
        return new User(registrationReqDto.getUsername(),
                registrationReqDto.getFirstName(),
                registrationReqDto.getLastName(),
                registrationReqDto.getEmail(),
                passwordEncoder.encode(registrationReqDto.getPassword()),
                null);
    }
}
