package com.raguzf.roommatch.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raguzf.roommatch.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
    
    Optional<Role> findByName(String role);
}
