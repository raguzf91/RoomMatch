package com.raguzf.roommatch.model;
import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;


    @NotEmpty(message = "USERNAME CANNOT BE EMPTY")
    @Column(name = "username", nullable = false)
    private String username;


    @NotEmpty(message = "EMAIL CANNOT BE EMPTY")
    @Email(message = "PLEASE ENTER A VALID EMAIL ADDRESS")
    @Column(name = "email", nullable = false)
    private String email;

    
    @NotEmpty(message = "PASSWORD CANNOT BE EMPTY")
    @Column(name = "password", nullable = false)
    private String password;

    @NotEmpty(message = "FIRST NAME CANNOT BE EMPTY")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotEmpty(message = "FIRST NAME CANNOT BE EMPTY")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "bio")
    private String bio;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "ProfileTags",
        joinColumns = @JoinColumn(name = "profile_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")

    )
    private Set<Tag> tags;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 99, message = "Age must not exceed 99")
    @Column(name = "age")
    private Integer age;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "User", fetch = FetchType.EAGER)
    private Set<Listing> listings;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "User", fetch = FetchType.EAGER)
    private Set<Photo> photos;


}

