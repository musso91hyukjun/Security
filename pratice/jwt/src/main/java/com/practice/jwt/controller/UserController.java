package com.practice.jwt.controller;

import com.practice.jwt.entity.User;
import com.practice.jwt.service.UserService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody User user) {
        User savedUser = null;
        ResponseEntity response = null;
        try {
            String hashPwd = passwordEncoder.encode(user.getUserpwd());
            user.setUserpwd(hashPwd);
            savedUser = userService.save(user);
            if (savedUser.getNo() > 0) {
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("success");
            }
        } catch (Exception e) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error");
        }
        return response;
    }

    @PostMapping("/loginOk")
    public ResponseEntity<String> login(@RequestBody User user) {
        ResponseEntity response = null;
        try {
            User findUser = userService.findByUserid(user.getUserid());
            if (findUser != null) {
                response = ResponseEntity
                        .status(HttpStatus.OK)
                        .body("success");
            } else {
                response = ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("not found");
            }
        } catch(Exception e) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error" + e.getMessage());
        }
        return response;
    }
}
