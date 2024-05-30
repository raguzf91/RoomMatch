package com.raguzf.roommatch.model.dto;
import com.raguzf.roommatch.model.Role;

public record UserDTO (
    String username,
    String email,
    String gender,
    Integer age,
    Role role

) {
   
}

