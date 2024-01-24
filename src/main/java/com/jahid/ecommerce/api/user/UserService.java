package com.jahid.ecommerce.api.user;

import com.jahid.ecommerce.api.utility.EnumConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    UserService(@Autowired UserRepository userRepository, @Autowired ModelMapper modelMapper){
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserDto registerUser(UserDto userDto){
        User user = this.modelMapper.map(userDto, User.class);
        if(user.getUserRole() == null) user.setUserRole(EnumConstants.UserRole.CUSTOMER);
        if(!user.isActive()) user.setActive(true);
        User createdUser = this.userRepository.save(user);
        return this.modelMapper.map(createdUser,UserDto.class);
    }

    public List<UserDto> getAllUser(){
        List<User> users = this.userRepository.findAll();
        List<UserDto> allUsers = new ArrayList<>();
        for(User user: users){
            allUsers.add(this.modelMapper.map(user,UserDto.class));
        }
        return allUsers;
    }

    public UserDto getUserByID(Long id){
        User user = this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return this.modelMapper.map(user, UserDto.class);
    }

    public UserDto updateUserNameEmailAndAddress(Long id, UserDto userDto){
        User existedUser = this.userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException(id));
        if(userDto.getEmail() != null) existedUser.setEmail(userDto.getEmail());
        if(userDto.getAddress() != null)existedUser.setAddress(userDto.getAddress());
        if(userDto.getName() != null)existedUser.setName(userDto.getName());
        User updatedUser = this.userRepository.save(existedUser);
        return this.modelMapper.map(updatedUser,UserDto.class);
    }

    public void deleteUser(Long id){
        User existedUser = this.userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException(id));
        this.userRepository.deleteById(id);
    }
}

