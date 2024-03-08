package com.practice.All.controller;

import com.practice.All.entity.UserEntity;
import com.practice.All.repository.UserRepository;
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
        try{
            userService.login(user);
            return ResponseEntity.status(HttpStatus.OK).body("good");
        } catch(UsernameNotFoundException e) {
            e.printStackTrace();
            System.out.println("예외발생: 아이디를 찾을 수 없습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("아이디를 찾을 수 없습니다.");
        } catch(BadCredentialsException e) {
            e.printStackTrace();
            System.out.println("예외발생: 비밀번호가 일치하지 않습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 일치하지 않습니다.");
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("예외발생: 알 수 없는 에러입니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알 수 없는 에러입니다.");
        }
    }


}
