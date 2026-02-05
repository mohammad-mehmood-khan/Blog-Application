package org.mehmood.blogapplicationbackendproject.impl;

import lombok.RequiredArgsConstructor;
import org.mehmood.blogapplicationbackendproject.Repository.RoleRepo;
import org.mehmood.blogapplicationbackendproject.Repository.UserRepo;
import org.mehmood.blogapplicationbackendproject.Service.UserService;
import org.mehmood.blogapplicationbackendproject.config.AppConstants;
import org.mehmood.blogapplicationbackendproject.entity.Role;
import org.mehmood.blogapplicationbackendproject.entity.User;
import org.mehmood.blogapplicationbackendproject.exceptions.ResourceNotFoundException;
import org.mehmood.blogapplicationbackendproject.payLoads.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;

    @Override
    public UserDto registerNewUser(UserDto userDto) {

        System.out.println("REGISTER SERVICE HIT");

        User user = modelMapper.map(userDto, User.class);

        String encoded = passwordEncoder.encode(userDto.getPassword());
        System.out.println("ENCODED PASSWORD = " + encoded);

        user.setPassword(encoded);

        Role role = roleRepo.findById(AppConstants.ROLE_USER).get();
        user.getRoles().add(role);

        User saved = userRepo.save(user);

        return modelMapper.map(saved, UserDto.class);
    }


    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow((() -> new ResourceNotFoundException("User", "Id", userId)));
        //inbuilt exception
/*        User user = this.userRepo.findById(userId)
               .orElseThrow(()->new RuntimeException("Resource not found")); */

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());
        User updatedUser = this.userRepo.save(user);

        return this.userToDto(updatedUser);
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));
        this.userRepo.delete(user);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        //can also use this
        //.orElseThrow((() -> new ResourceNotFoundException("User", "Id", userId)));
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));

        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> allUsers = this.userRepo.findAll();

        return allUsers.stream()
                .map(this::userToDto)
                .collect(Collectors.toList());
    }

    private User dtoToUser(UserDto userDto) {
//        User user = new User();
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setAbout(userDto.getAbout());
//        user.setPassword(userDto.getPassword());
        return this.modelMapper.map(userDto, User.class);
    }

    public UserDto userToDto(User user) {
//        UserDto userDto = new UserDto();
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setAbout(user.getAbout());
        return this.modelMapper.map(user, UserDto.class);

    }
}
