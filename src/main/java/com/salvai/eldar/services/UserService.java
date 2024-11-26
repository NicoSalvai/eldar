package com.salvai.eldar.services;

import com.salvai.eldar.models.api.UserDto;
import com.salvai.eldar.models.api.UserRequest;
import com.salvai.eldar.models.jpa.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserDto addUser(UserRequest userRequest);

    Page<UserDto> getUsers(Pageable pageable);

    UserDto getUserById(Integer userId);

    UserEntity getUserEntityById(Integer userId);

    void deleteUserById(Integer userId);

    UserDto updateUserById(Integer userId, UserRequest userRequest);
}
