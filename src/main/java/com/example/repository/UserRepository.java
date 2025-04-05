package com.example.repository;

import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository  // ✅ Ensure this annotation is present
public interface UserRepository extends JpaRepository<User, Long> {
   Optional<User> findByEmail(String email);
   Optional<User>findByUserId(long id);
   Optional<User>findByPhone(String phone);
   User findById(long id);
}