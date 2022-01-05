package com.adulger.bellapi.service;

import com.adulger.bellapi.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by adulger on 24.12.2021
 **/
@Service
public interface UserServiceImpl extends CrudRepository<User, Long> {
    @Query("SELECT p FROM User p WHERE LOWER(p.email) = LOWER(:email) and p.password = :password")
    User findBy(@Param("email") String email, @Param("password") String password);

    @Query("SELECT p FROM User p WHERE p.userUniqueId = :userUniqueId")
    User findBy(@Param("userUniqueId") String userUniqueId);
}
