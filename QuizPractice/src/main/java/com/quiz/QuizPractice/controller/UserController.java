/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quiz.QuizPractice.controller;

import com.quiz.QuizPractice.model.User;
import com.quiz.QuizPractice.repository.UserRepository;
import com.quiz.QuizPractice.security.JwtResponse;
import com.quiz.QuizPractice.security.JwtUtils;
import com.quiz.QuizPractice.security.LoginRequest;
import jakarta.validation.Valid;
import java.util.List;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author nhat
 */
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("users")
@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;
    
    @GetMapping 
    public List<User> getAllFeature() {
        return userRepository.findAll();
    }
    
//public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
//    String username = loginRequest.getUsername();
//    String password = loginRequest.getPassword();
//
//    User user = userRepository.findByUsername(username);
//
//    if (user == null) {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//    }
//
//    if (!user.getPassword().equals(password)) {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//    }
//
//    Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
//    SecurityContextHolder.getContext().setAuthentication(authentication);
//
//    String jwt = JwtUtils.generateToken(user.getUsername());
//    JwtResponse jwtResponse = new JwtResponse(jwt, user.getUsername());
//
//    return ResponseEntity.ok(jwtResponse);
//}
    
    @GetMapping("/login")
    User loginUser(@RequestBody User user) {
    User foundUser = userRepository.findByUsername(user.getUsername());
    if (foundUser != null && foundUser.getPassword().equals(user.getPassword())) {
        return foundUser;
    } else {
        return null;
    }
}
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }

        String emailRegex = "^[a-zA-Z0-9._%+-]+@gmail.com$";
        Pattern pattern = Pattern.compile(emailRegex);

    if (userRepository.existsByEmail(user.getEmail()) || !pattern.matcher(user.getEmail()).matches()) {
        return ResponseEntity.badRequest().body("Error: Invalid or already used email address!");
    }

        // encode the password before saving the user
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }
    
}
