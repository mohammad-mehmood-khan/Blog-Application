package org.mehmood.blogapplicationbackendproject.Controller;

import lombok.RequiredArgsConstructor;
import org.mehmood.blogapplicationbackendproject.Service.UserService;
import org.mehmood.blogapplicationbackendproject.payLoads.JwtAuthRequest;
import org.mehmood.blogapplicationbackendproject.payLoads.JwtAuthResponse;
import org.mehmood.blogapplicationbackendproject.payLoads.UserDto;
import org.mehmood.blogapplicationbackendproject.security.JwtAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtAuthService jwtAuthService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        //generate JWT
        String token = jwtAuthService.generateToken(request.getUsername());

        // return response
        return ResponseEntity.ok(new JwtAuthResponse(token, "Bearer", request.getUsername()));
    }

    @PostMapping("/registerUser")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
        UserDto regisUser = this.userService.registerNewUser(userDto);
        return new ResponseEntity<>(regisUser, HttpStatus.CREATED);
    }
}
