package com.ecommerce.api.user.controller;

import com.ecommerce.api.user.request.UserRequest;
import com.ecommerce.api.user.response.UserResponse;
import com.ecommerce.api.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor( onConstructor_ = @Autowired )
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping( "/register" )
    public ResponseEntity<UserResponse> registerUser( @RequestBody @Valid UserRequest userRequest ){
        UserResponse createdUser = userService.registerUser( userRequest );
        return new ResponseEntity<>( createdUser, HttpStatus.CREATED );
    }

    @PostMapping( "/login" )
    public ResponseEntity<Void> login( @RequestBody UserRequest userRequest ){
        userService.login( userRequest );
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUser(){
        return new ResponseEntity<>( userService.getAllUser(), HttpStatus.OK );
    }

    @GetMapping( "/{id}" )
    public ResponseEntity<UserResponse> getUserById( @PathVariable Long id ){
        UserResponse user = userService.getUserByID( id );
        return new ResponseEntity<>( user, HttpStatus.OK );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> updateUserNameEmailAndAddress(
            @PathVariable Long id, @RequestBody UserRequest user
    ){
        UserResponse updatedUser = userService.updateUserNameEmailAndAddress( id, user );
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping( "/{id}" )
    public ResponseEntity<Void> deleteUserById( @PathVariable Long id ){
        userService.deleteUser( id );
        return ResponseEntity.noContent().build();
    }
}
