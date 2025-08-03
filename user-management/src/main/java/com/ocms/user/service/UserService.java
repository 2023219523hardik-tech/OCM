package com.ocms.user.service;

import com.ocms.common.exception.CustomExceptions;
import com.ocms.user.dto.UserRegistrationDto;
import com.ocms.user.entity.User;
import com.ocms.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    // HashMap to store user enrollments (username -> enrolled courses)
    private final Map<String, User> userEnrollments = new HashMap<>();

    public User registerUser(UserRegistrationDto registrationDto) {
        // Validate role
        if (!registrationDto.getRole().equalsIgnoreCase("STUDENT") && 
            !registrationDto.getRole().equalsIgnoreCase("INSTRUCTOR")) {
            throw new CustomExceptions.ValidationException("Role must be either STUDENT or INSTRUCTOR");
        }

        // Check for duplicates
        if (userRepository.existsByUsername(registrationDto.getUsername())) {
            throw new CustomExceptions.DuplicateResourceException("Username already exists");
        }
        
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new CustomExceptions.DuplicateResourceException("Email already exists");
        }

        // Create and save user
        User user = new User(
            registrationDto.getUsername(),
            registrationDto.getEmail(),
            registrationDto.getRole().toUpperCase()
        );
        
        User savedUser = userRepository.save(user);
        
        // Store in HashMap for enrollment tracking
        userEnrollments.put(savedUser.getUsername(), savedUser);
        
        return savedUser;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("User not found with id: " + userId));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("User not found with username: " + username));
    }

    public Map<String, User> getUserEnrollments() {
        return new HashMap<>(userEnrollments);
    }

    public boolean isInstructor(Long userId) {
        User user = getUserById(userId);
        return "INSTRUCTOR".equals(user.getRole());
    }

    public boolean isStudent(Long userId) {
        User user = getUserById(userId);
        return "STUDENT".equals(user.getRole());
    }
}