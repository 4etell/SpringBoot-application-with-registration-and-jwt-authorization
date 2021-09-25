package com.foretell.jwtauthenticationapp.util.jwt;

import com.foretell.jwtauthenticationapp.model.User;
import com.foretell.jwtauthenticationapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }


    @Override
    public JwtUser loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        return JwtUser.fromUserToJwtUser(user);
    }
}
