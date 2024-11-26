package com.salvai.eldar.services.impl;

import com.salvai.eldar.models.api.UserDto;
import com.salvai.eldar.models.api.UserRequest;
import com.salvai.eldar.models.jpa.UserEntity;
import com.salvai.eldar.repositories.UserRepository;
import com.salvai.eldar.services.UserService;
import com.salvai.eldar.transformers.UserEntityToDtoTransformer;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @NonNull
    private final UserRepository userRepository;

    @NonNull
    private final UserEntityToDtoTransformer userEntityToDtoTransformer;

    @Override
    public UserDto addUser(UserRequest userRequest) {
        if(userRepository.findByDni(userRequest.dni()).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "User with dni %s already exists".formatted(userRequest.dni()));
        }

        final var userEntity = new UserEntity();
        userEntity.setFirstName(userRequest.firstName());
        userEntity.setLastName(userRequest.lastName());
        userEntity.setDni(userRequest.dni());
        userEntity.setBirthDate(userRequest.birthDate());
        userEntity.setEmail(userRequest.email());

        return userEntityToDtoTransformer.convert(userRepository.save(userEntity));
    }

    @Override
    public Page<UserDto> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userEntityToDtoTransformer::convert);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        return userEntityToDtoTransformer.convert(getUserEntityById(userId));
    }

    @Override
    public UserEntity getUserEntityById(Integer userId){

        return userRepository.findById(userId).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @Override
    public void deleteUserById(Integer userId) {
        final var userEntity = getUserEntityById(userId);
        userRepository.delete(userEntity);
    }

    @Override
    public UserDto updateUserById(Integer userId, UserRequest userRequest) {
        final var userEntity = getUserEntityById(userId);
        if(!userEntity.getDni().equals(userRequest.dni()) && userRepository.findByDni(userRequest.dni()).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "A different user with dni %s already exists".formatted(userRequest.dni()));
        }

        userEntity.setFirstName(userRequest.firstName());
        userEntity.setLastName(userRequest.lastName());
        userEntity.setDni(userRequest.dni());
        userEntity.setBirthDate(userRequest.birthDate());
        userEntity.setEmail(userRequest.email());

        return userEntityToDtoTransformer.convert(userRepository.save(userEntity));
    }
}
