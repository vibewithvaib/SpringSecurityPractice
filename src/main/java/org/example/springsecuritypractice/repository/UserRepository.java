package org.example.springsecuritypractice.repository;

import org.example.springsecuritypractice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    // Change the return type to Optional<User>
    Optional<User> findByEmail(String email);
}
