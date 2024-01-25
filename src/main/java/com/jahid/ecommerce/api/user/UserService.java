package com.jahid.ecommerce.api.user;

import com.jahid.ecommerce.api.utility.EnumConstants;
import com.jahid.ecommerce.api.utility.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    UserService(@Autowired UserRepository userRepository, @Autowired ModelMapper modelMapper){
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserResponseDto registerUser(UserDto userDto){
        User user = this.modelMapper.map(userDto, User.class);
        if(user.getUserRole() == null) user.setUserRole(EnumConstants.UserRole.CUSTOMER);
        if(!user.isActive()) user.setActive(true);
        User createdUser = this.userRepository.save(user);
        return this.modelMapper.map(createdUser,UserResponseDto.class);
    }

    public void login(UserDto userDto){
        User existedUser = this.userRepository.findByMobileNo(userDto.getMobileNo());
        if(existedUser == null) throw new NotFoundException();
        if(!Objects.equals(existedUser.getPassword(), userDto.getPassword())) throw new PasswordNotMatchException();
    }

    public List<UserResponseDto> getAllUser(){
        List<User> users = this.userRepository.findAll();
        List<UserResponseDto> allUsers = users.stream().map(user -> modelMapper.map(user, UserResponseDto.class)).collect(Collectors.toList());
        return allUsers;
    }

    public UserResponseDto getUserByID(Long id){
        User user = this.userRepository.findById(id).orElseThrow(() -> new NotFoundException(id, User.class.getSimpleName()));
        return this.modelMapper.map(user, UserResponseDto.class);
    }

    public UserResponseDto updateUserNameEmailAndAddress(Long id, UserDto userDto){
        User existedUser = this.userRepository.findById(id)
                .orElseThrow(()->new NotFoundException(id, User.class.getSimpleName()));
        if(userDto.getEmail() != null) existedUser.setEmail(userDto.getEmail());
        if(userDto.getAddress() != null)existedUser.setAddress(userDto.getAddress());
        if(userDto.getName() != null)existedUser.setName(userDto.getName());
        User updatedUser = this.userRepository.save(existedUser);
        return this.modelMapper.map(updatedUser,UserResponseDto.class);
    }

    public void deleteUser(Long id){
        User existedUser = this.userRepository.findById(id)
                .orElseThrow(()->new NotFoundException(id,User.class.getSimpleName()));
        this.userRepository.deleteById(id);
    }
}

