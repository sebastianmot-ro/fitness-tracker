package com.fitness.tracker.fitness_tracker.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserRequest {

    @NotBlank(message = "Name must be between 2 and 50 characters")
    @Size(min = 2, max = 50)
    private String fullName;

    @NotBlank (message = "Invalid email")
    @Email
    private String email;

    @NotBlank (message = "Password must be minimum 6 characters")
    @Size(min = 6)
    private String password; //plain text momentan pana implementam security
}
