package com.salvai.eldar.controllers;

import com.salvai.eldar.models.api.UserDto;
import com.salvai.eldar.models.api.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users")
@Tag(name = "Users", description = "User management resources")
public interface UserController {

    @Operation(summary = "Create a new user")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "User created successfully"),
        @ApiResponse(responseCode = "400", description = "Problem with request body"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    ResponseEntity<Void> addUser(@Valid @RequestBody UserRequest userRequest);

    @Operation(summary = "Get users")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Users found"),
        @ApiResponse(responseCode = "400", description = "Problem with request"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    ResponseEntity<Page<UserDto>> getUsers(@ParameterObject @PageableDefault Pageable pageable);

    @Operation(summary = "Get user by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "400", description = "Problem with request"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{userId}")
    ResponseEntity<UserDto> getUserById(@Parameter @PathVariable("userId") Integer userId);

    @Operation(summary = "Delete user by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "400", description = "Problem with request"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{userId}")
    ResponseEntity<Void> deleteUserById(@Parameter @PathVariable("userId") Integer userId);

    @Operation(summary = "Update user by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "400", description = "Problem with request"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{userId}")
    ResponseEntity<UserDto> updateUserById(@Parameter @PathVariable("userId") Integer userId,
       @Valid @RequestBody UserRequest userRequest);
}
