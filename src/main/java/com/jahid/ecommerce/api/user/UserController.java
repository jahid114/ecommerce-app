package com.jahid.ecommerce.api.user;

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
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto user){
        UserDto createdUser = userService.registerUser(user);
        return new ResponseEntity<UserDto>(createdUser,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUser(){
        return new ResponseEntity<>(this.userService.getAllUser(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id){
        UserDto user = this.userService.getUserByID(Long.parseLong(id));
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> updateUserNameEmailAndAddress(@PathVariable String id, @RequestBody UserDto user){
        UserDto updatedUser = this.userService.updateUserNameEmailAndAddress(Long.parseLong(id),user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable String id){
        this.userService.deleteUser(Long.parseLong(id));
        return ResponseEntity.noContent().build();
    }


}
