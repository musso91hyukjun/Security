package com.practice.jwt.service;

import com.practice.jwt.entity.User;
import com.practice.jwt.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    List<User> findByEmail(String email) {

        return userRepository.findByUseremail(email);
    }

    @Transactional
    public User save(User user) {

        return userRepository.save(user);
    }

    public User findByUserid(String userid) {

        return userRepository.findByUserid(userid);
    }
}
