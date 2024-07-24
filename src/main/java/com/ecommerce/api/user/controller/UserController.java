package com.ecommerce.api.user.controller;

import com.ecommerce.api.user.request.UserRequest;
import com.ecommerce.api.user.response.UserResponse;
import com.ecommerce.api.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    UserController(@Autowired UserService userService){
        this.userService = userService;
    }
    @PostMapping()
    public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid UserRequest user){
        UserResponse createdUser = userService.registerUser(user);
        return new ResponseEntity<UserResponse>(createdUser,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody UserRequest userRequest){
        this.userService.login(userRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUser(){
        return new ResponseEntity<>(this.userService.getAllUser(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable String id){
        UserResponse user = this.userService.getUserByID(Long.parseLong(id));
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> updateUserNameEmailAndAddress(@PathVariable String id, @RequestBody UserRequest user){
        UserResponse updatedUser = this.userService.updateUserNameEmailAndAddress(Long.parseLong(id),user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable String id){
        this.userService.deleteUser(Long.parseLong(id));
        return ResponseEntity.noContent().build();
    }
}
