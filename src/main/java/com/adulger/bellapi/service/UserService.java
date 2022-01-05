package com.adulger.bellapi.service;

import com.adulger.bellapi.dto.UserDTO;
import com.adulger.bellapi.model.User;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by adulger on 25.12.2021
 **/

@Service
public class UserService extends BaseService{

    @Autowired
    private UserServiceImpl userServiceImpl;

    public void init(int count) {
        Faker faker = new Faker();

        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setEmail(faker.internet().emailAddress());
            user.setFullName(faker.name().fullName());
            user.setUserUniqueId(UUID.randomUUID().toString());
            user.setPassword(faker.internet().password());
            userServiceImpl.save(user);
        }

    }

    public User findBy(String email, String password) {
        User user = userServiceImpl.findBy(email, password);
        return user;
    }

    public List<UserDTO> findAll() {
        return getListOfObjectFromIterable(userServiceImpl.findAll());
    }

    private List<UserDTO> getListOfObjectFromIterable(Iterable<User> iterable) {
        List<UserDTO> userDTOList = new ArrayList<>();
        Iterator<User> userIterator = iterable.iterator();
        while (userIterator.hasNext()){
            userDTOList.add(userIterator.next().toDTO());
        }
        return userDTOList;
    }

    public Optional<User> findById(Long userId) {
        return userServiceImpl.findById(userId);
    }

    public User findBy(String userUniqueId) {
        return userServiceImpl.findBy(userUniqueId);
    }

    public UserDTO login() {
        return getAuthUser().toDTO();
    }
}
