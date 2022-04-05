package com.springboot.blog.controller;

import com.springboot.blog.dto.LoginDto;
import com.springboot.blog.dto.SignUpDto;
import com.springboot.blog.model.Role;
import com.springboot.blog.model.User;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseEntity<>("User signed-in successfully!", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody SignUpDto signUpDto) {
        // check if uesrname already exists
        if(userRepository.existsByName(signUpDto.getUsername())){
            return  new ResponseEntity<>("User name already exists!", HttpStatus.BAD_REQUEST);
        }

        // check if EMAIL already exists
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return  new ResponseEntity<>("Email already exists!", HttpStatus.BAD_REQUEST);
        }

        // create new user
        User user = new User();
        user.setEmail(signUpDto.getEmail());
        user.setName(signUpDto.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role role = roleRepository.findByName("admin").get();

        user.setRoleSet(Collections.singleton(role));

        userRepository.save(user);

        return new ResponseEntity<>("User signed up successfully", HttpStatus.OK);
    }

}
