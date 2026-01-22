package org.mehmood.blogapplicationbackendproject.Service;

import org.mehmood.blogapplicationbackendproject.payLoads.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto user);

    UserDto updateUser(UserDto user, Integer userId);

    void deleteUser(Integer userId);

    UserDto getUserById(Integer userId);

    List<UserDto> getAllUsers();
}
