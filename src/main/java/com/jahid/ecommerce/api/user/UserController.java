package com.jahid.ecommerce.api.user;

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
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody @Valid UserDto user){
        UserResponseDto createdUser = userService.registerUser(user);
        return new ResponseEntity<UserResponseDto>(createdUser,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody UserDto userDto){
        this.userService.login(userDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUser(){
        return new ResponseEntity<>(this.userService.getAllUser(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable String id){
        UserResponseDto user = this.userService.getUserByID(Long.parseLong(id));
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUserNameEmailAndAddress(@PathVariable String id, @RequestBody UserDto user){
        UserResponseDto updatedUser = this.userService.updateUserNameEmailAndAddress(Long.parseLong(id),user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable String id){
        this.userService.deleteUser(Long.parseLong(id));
        return ResponseEntity.noContent().build();
    }
}
