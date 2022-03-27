package com.example.mongorestapi.service;

import com.example.mongorestapi.dto.request.UserRequest;
import com.example.mongorestapi.dto.request.UpdateUserRequest;
import com.example.mongorestapi.dto.response.UpdateUserResponse;
import com.example.mongorestapi.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse findUserById(Long id);

    List<UserResponse> findAllUser(int pageNo, int pageSize);

    UserResponse createUser(UserRequest request);

    UpdateUserResponse updateUser(Long id, UpdateUserRequest request);

    UserResponse removeUserById(Long id);
}
