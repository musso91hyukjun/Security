package com.practice.All.service;

import com.practice.All.entity.UserEntity;
import com.practice.All.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean isEmailDuplicate (String email) {
        List<UserEntity> user = userRepository.findByEmail(email);
        return !user.isEmpty();
    }

    public void save(UserEntity user) {
        user.setPwd(passwordEncoder.encode(user.getPwd()));
        userRepository.save(user);
    }

    public UserEntity login(UserEntity user) {
        List<UserEntity> users = userRepository.findByEmail(user.getEmail());
        if(users.isEmpty()){
            throw new UsernameNotFoundException("not found");
        }
        UserEntity storedUser = users.get(0);
        if(!passwordEncoder.matches(user.getPwd(), storedUser.getPwd())) {
            throw new BadCredentialsException("bad");
        }
        return storedUser;
    }

}
