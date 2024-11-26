package com.salvai.eldar.controllers.impl;

import com.salvai.eldar.controllers.UserController;
import com.salvai.eldar.models.api.UserDto;
import com.salvai.eldar.models.api.UserRequest;
import com.salvai.eldar.services.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    @NonNull
    private final UserService userService;

    @Override
    public ResponseEntity<Void> addUser(UserRequest userRequest) {
        final var user = userService.addUser(userRequest);

        final var location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(user.getId())
            .toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<Page<UserDto>> getUsers(Pageable pageable) {
        return ResponseEntity.ok(userService.getUsers(pageable));
    }

    @Override
    public ResponseEntity<UserDto> getUserById(Integer userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @Override
    public ResponseEntity<Void> deleteUserById(Integer userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<UserDto> updateUserById(Integer userId, UserRequest userRequest) {
        return ResponseEntity.ok(userService.updateUserById(userId, userRequest));
    }
}
