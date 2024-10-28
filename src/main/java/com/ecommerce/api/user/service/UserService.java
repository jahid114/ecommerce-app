package com.ecommerce.api.user.service;

import com.ecommerce.api.user.UserRole;
import com.ecommerce.api.user.exeptions.PasswordNotMatchException;
import com.ecommerce.api.user.response.UserResponse;
import com.ecommerce.api.user.model.User;
import com.ecommerce.api.user.request.UserRequest;
import com.ecommerce.api.utility.EnumConstants;
import com.ecommerce.api.utility.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public UserResponse registerUser(UserRequest userRequest){
        User user = new User();
        BeanUtils.copyProperties( userRequest, user );
        User createdUser = userRepository.save(user);
        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(createdUser, response);
        return response;
    }

    public void login(UserRequest userRequest){
        User existedUser = this.userRepository.findByMobileNo(userRequest.getMobileNo());
        if(!Objects.equals(existedUser.getPassword(), userRequest.getPassword())) throw new PasswordNotMatchException();
    }

    public List<UserResponse> getAllUser(){
        List<User> users = this.userRepository.findAll();
        return users.stream().map(user -> modelMapper.map(user, UserResponse.class)).collect(Collectors.toList());
    }

    public UserResponse getUserByID(Long id){
        User user = this.userRepository.findById(id).orElseThrow(() -> new NotFoundException(id, User.class.getSimpleName()));
        return this.modelMapper.map(user, UserResponse.class);
    }

    public UserResponse updateUserNameEmailAndAddress(Long id, UserRequest userRequest){
        User existedUser = this.userRepository.findById(id)
                .orElseThrow(()->new NotFoundException(id, User.class.getSimpleName()));
        if(userRequest.getEmail() != null) existedUser.setEmail(userRequest.getEmail());
        if(userRequest.getAddress() != null)existedUser.setAddress(userRequest.getAddress());
        if(userRequest.getName() != null)existedUser.setName(userRequest.getName());
        User updatedUser = this.userRepository.save(existedUser);
        return this.modelMapper.map(updatedUser, UserResponse.class);
    }

    public void deleteUser(Long id){
        User existedUser = this.userRepository.findById(id)
                .orElseThrow(()->new NotFoundException(id,User.class.getSimpleName()));
        this.userRepository.deleteById(existedUser.getId());
    }
}

