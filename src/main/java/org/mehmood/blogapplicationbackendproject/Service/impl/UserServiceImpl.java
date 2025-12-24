package org.mehmood.blogapplicationbackendproject.Service.impl;

import org.mehmood.blogapplicationbackendproject.Repository.UserRepo;
import org.mehmood.blogapplicationbackendproject.Service.UserService;
import org.mehmood.blogapplicationbackendproject.entity.User;
import org.mehmood.blogapplicationbackendproject.exceptions.ResourceNotFoundException;
import org.mehmood.blogapplicationbackendproject.payLoads.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

public UserServiceImpl(UserRepo userRepo,ModelMapper modelMapper){
    this.userRepo=userRepo;
    this.modelMapper=modelMapper;
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
        User user=this.userRepo.findById(userId)
                .orElseThrow(()-> new RuntimeException("user not found"));
        this.userRepo.delete(user);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        //can also use this
        //.orElseThrow((() -> new ResourceNotFoundException("User", "Id", userId)));
        User user=this.userRepo.findById(userId)
                .orElseThrow(()-> new RuntimeException("user not found"));

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
        return this.modelMapper.map(user,UserDto.class);

    }
}
