/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.quiz.QuizPractice.repository;

import com.quiz.QuizPractice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author nhat
 */
public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
