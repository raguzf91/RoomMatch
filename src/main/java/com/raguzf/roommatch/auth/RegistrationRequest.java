package com.raguzf.roommatch.auth;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationRequest {

    @NotEmpty(message = "First Name Cannot Be Empty")
    @NotBlank(message = "First Name Cannot Be Blank")
    private String firstName; 

    @NotEmpty(message = "Last Name Cannot Be Empty")
    @NotBlank(message = "Last Name Cannot Be Blank")
    private String lastName;

    @NotNull(message = "Date Of Birth Cannot Be Null")
    private String dateOfBirth;

    @NotEmpty(message = "Email Cannot Be Empty")
    @NotBlank(message = "Email Cannot Be Blank")
    @Email(message = "Email is incorrect")
    private String email;

    @NotEmpty(message = "Username Cannot Be Empty")
    @NotBlank(message = "Username Cannot Be Blank")
    private String username;

    @NotEmpty(message = "Password Cannot Be Empty")
    @NotBlank(message = "Password Cannot Be Blank")
    @Size(min = 8, message = "Password Must Be At Least 8 Characters Long")
    private String password;

    @NotEmpty(message = "Gender Cannot Be Null")
    private String gender;
}
