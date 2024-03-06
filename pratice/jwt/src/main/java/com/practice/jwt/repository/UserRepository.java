package com.practice.jwt.repository;

import com.practice.jwt.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {


    List<User> findByUseremail(String email);

    User findByUserid(String userid);
}
