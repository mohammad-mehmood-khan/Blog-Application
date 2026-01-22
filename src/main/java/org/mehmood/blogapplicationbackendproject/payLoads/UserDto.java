package org.mehmood.blogapplicationbackendproject.payLoads;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDto {

    private Integer id;
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email address is not valid")
    private String email;
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    private String password;
    @NotBlank(message = "About section cannot be empty")
    @Size(max = 200, message = "About section must not exceed 200 characters")
    private String about;
}
