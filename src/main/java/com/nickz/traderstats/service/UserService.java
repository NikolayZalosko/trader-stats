package com.nickz.traderstats.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.nickz.traderstats.dto.UserSavingDto;
import com.nickz.traderstats.exception.EmailAlreadyExistsException;
import com.nickz.traderstats.exception.ResourceNotFoundException;
import com.nickz.traderstats.model.User;

public interface UserService extends UserDetailsService {
    List<User> findAll();

    User findById(int userId) throws ResourceNotFoundException;

    User findByEmail(String email) throws ResourceNotFoundException;

    User getOne(int userId) throws ResourceNotFoundException; /* returns a proxy object */

    User save(UserSavingDto userDto) throws EmailAlreadyExistsException;

    User update(User user) throws ResourceNotFoundException;

    void delete(int id) throws ResourceNotFoundException;
}
