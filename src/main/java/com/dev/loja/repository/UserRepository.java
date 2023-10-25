package com.dev.loja.repository;

import com.dev.loja.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByLogin(String login);
    Optional<User> findUserByLogin(String login);

}
