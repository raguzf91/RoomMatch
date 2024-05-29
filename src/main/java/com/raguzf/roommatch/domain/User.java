package com.raguzf.roommatch.domain;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class User {
    private Integer id;
    @NotEmpty(message = "USERNAME CANNOT BE EMPTY")
    private String username;
    @NotEmpty(message = "EMAIL CANNOT BE EMPTY")
    @Email(message = "PLEASE ENTER A VALID EMAIL ADDRESS")
    private String email;
    @NotEmpty(message = "PASSWORD CANNOT BE EMPTY")
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer roleId;
}


/*
 * 	id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    role_id INT NOT NULL,
    FOREIGN KEY (role_id) REFERENCES Roles(id) ON DELETE CASCADE
 * 
 */
