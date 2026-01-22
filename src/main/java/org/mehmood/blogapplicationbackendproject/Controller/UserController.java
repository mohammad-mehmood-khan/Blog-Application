package org.mehmood.blogapplicationbackendproject.Controller;

import jakarta.validation.Valid;
import org.mehmood.blogapplicationbackendproject.Service.UserService;
import org.mehmood.blogapplicationbackendproject.payLoads.CustomApiResponse;
import org.mehmood.blogapplicationbackendproject.payLoads.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    public UserController (UserService userService){
        this.userService=userService;
    }


    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        return new ResponseEntity<>
                (userService.createUser(userDto), HttpStatus.CREATED);
    }


    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable Integer userId) {
        return new ResponseEntity<>
                (userService.updateUser(userDto, userId), HttpStatus.ACCEPTED);
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<CustomApiResponse> deleteUser(@PathVariable Integer userId){
       userService.deleteUser(userId);
        return new ResponseEntity<>
                (new CustomApiResponse("user deleted successfully", true), HttpStatus.OK);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer userId){
        return new ResponseEntity<>
                (userService.getUserById(userId),HttpStatus.OK);
    }


    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDto>>getAllUsers(){
        return new ResponseEntity<>
                (userService.getAllUsers(),HttpStatus.OK);
    }
}
