package org.mehmood.blogapplicationbackendproject.payLoads;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDto {
    private int id;
    private String name;
    private String email;
    private String password;
    private String about;
}
