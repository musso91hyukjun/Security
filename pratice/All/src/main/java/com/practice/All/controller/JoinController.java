package com.practice.All.controller;

import com.practice.All.entity.UserEntity;
import com.practice.All.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class JoinController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    public JoinController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UserEntity user) {

        if(userService.isEmailDuplicate(user.getEmail())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Duplicate");
        } else {
            userService.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("success");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserEntity user) {
        try {
            UserEntity loggedInUser = userService.login(user);
            // 로그인에 성공한 경우
            return ResponseEntity.status(HttpStatus.OK).body("Login successful!");
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            // 로그인에 실패한 경우
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " + e.getMessage());
        } catch (Exception e) {
            // 다른 예외가 발생한 경우
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during login.");
        }
    }

}
