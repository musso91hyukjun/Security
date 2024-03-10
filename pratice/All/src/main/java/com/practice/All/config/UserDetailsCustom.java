package com.practice.All.config;


import com.practice.All.entity.UserEntity;
import com.practice.All.repository.UserRepository;
import com.practice.All.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsCustom implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsCustom(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String Email = null;
        String pwd = null;
        List<GrantedAuthority> authorities = null;

        List<UserEntity> user = userRepository.findByEmail(email);
        if(user.isEmpty()) {
           throw new UsernameNotFoundException(email);
        }
        Email = user.get(0).getEmail();
        pwd = user.get(0).getPwd();
        authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.get(0).getRole()));

        return new User(email, pwd, authorities);
    }
}
