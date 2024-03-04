package com.prac.join.controller;

import com.prac.join.model.User;
import com.prac.join.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RestController
public class JoinController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public JoinController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody User user) {
        User savedUser = null;
        ResponseEntity response = null;

        try{
            String hashPwd = passwordEncoder.encode(user.getPwd());
            user.setPwd(hashPwd);
            user.setCreateDt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            savedUser = userService.save(user);
            if(savedUser.getId() > 0) {
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("success");
            }
        } catch(Exception e) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("server error" + e.getMessage());
        }
        return response;
    }
}
