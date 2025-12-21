package org.mehmood.blogapplicationbackendproject.Controller;

import org.mehmood.blogapplicationbackendproject.Service.UserService;
import org.mehmood.blogapplicationbackendproject.payLoads.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable int userId) {
        return new ResponseEntity<>(userService.updateUser(userDto, userId), HttpStatus.ACCEPTED);
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable int userId){
       userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable int userId){
        return new ResponseEntity<>(userService.getUserById(userId),HttpStatus.OK);
    }
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDto>>getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }
}
