/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quiz.QuizPractice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.MediaType;
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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
    
     @Autowired
    private JavaMailSender javaMailSender;
    
    @GetMapping 
    public List<User> getAllFeature() {
        return userRepository.findAll();
    }
    
    @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody User user) {
    String username = user.getUsername();
    String password = user.getPassword();

    // Perform authentication logic here
    User userfounder = userRepository.findByUsername(username);

    if (userfounder == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"error\":\"Invalid credentials\"}");
    }

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    if (!passwordEncoder.matches(password, userfounder.getPassword())) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"error\":\"Invalid credentials\"}");
    }

    // Authentication successful, return a success message
    String responseJson = "{\"message\":\"Login successful\"}";
    return ResponseEntity.ok(responseJson);
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

        // Encode the password before saving the user
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        
        // Save the user
        userRepository.save(user);
        
        // Send confirmation email
        sendConfirmationEmail(user.getEmail());
        
        return ResponseEntity.ok("User registered successfully!");
    }
    
    private void sendCon
            
            
            
            
            
            firmationEmail(String recipientEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Account Confirmation");
        message.setText("Please click the link below to confirm your registration:\n\n"
                + "http://yourdomain.com/confirm?email=" + recipientEmail);
        
        javaMailSender.send(message);
    }
    
}
