package com.brewbox.model.DTOs;

import com.brewbox.model.validation.FieldMatch;
import com.brewbox.model.validation.UniqueUserEmail;
import com.brewbox.model.validation.UniqueUsername;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@FieldMatch(
        first = "password",
        second = "confirmPassword",
        message = "Passwords do not match!"
)
public class UserRegisterDTO {

    @NotEmpty
    @Size(min = 2, max = 20)
    private String firstName;

    private String middleName;

    @NotEmpty
    @Size(min = 2, max = 20)
    private String lastName;

    @NotEmpty(message = "Enter your email.")
    @Email(message = "Invalid email.")
    @UniqueUserEmail(message = "This email has been already used.")
    private String email;

    @NotEmpty
    @Size(min = 5, max = 20)
    @UniqueUsername
    private String username;

    private final LocalDate created = LocalDate.now();

    @NotEmpty
    @Size(min = 5)
    private String password;

    private String confirmPassword;

}
