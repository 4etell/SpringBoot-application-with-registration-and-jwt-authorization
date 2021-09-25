package com.foretell.jwtauthenticationapp.repository;

import com.foretell.jwtauthenticationapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    User findByUsername(String name);
}
