package com.raguzf.roommatch.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raguzf.roommatch.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
   
    Optional<User> findByEmail(String email);

    
}
