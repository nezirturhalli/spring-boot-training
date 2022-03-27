package com.example.mongorestapi.service.impl;

import com.example.mongorestapi.domain.User;
import com.example.mongorestapi.dto.request.UpdateUserRequest;
import com.example.mongorestapi.dto.request.UserRequest;
import com.example.mongorestapi.dto.response.UpdateUserResponse;
import com.example.mongorestapi.dto.response.UserResponse;
import com.example.mongorestapi.exception.UserNotFoundException;
import com.example.mongorestapi.repository.UserRepository;
import com.example.mongorestapi.service.SequenceGeneratorService;
import com.example.mongorestapi.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private static final String debugId = UUID.randomUUID().toString();
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final SequenceGeneratorService generatorService;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, SequenceGeneratorService generatorService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.generatorService = generatorService;
    }

    @Override
    public UserResponse findUserById(Long id) {
        return modelMapper.map(userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found! ID: ",
                                id.toString(),
                                debugId)), UserResponse.class);
    }

    @Override
    public List<UserResponse> findAllUser(int pageNo, int pageSize) {
        return userRepository.findAll(PageRequest.of(pageNo, pageSize))
                .stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .sorted(Comparator.comparing(UserResponse::getFullname))
                .toList();
    }

    @Override
    public UserResponse createUser(UserRequest request) {

        var user = modelMapper.map(request, User.class);
        user.setId(generatorService.generateSequence(User.SEQUENCE_NAME));
        return modelMapper.map(userRepository.save(user), UserResponse.class);
    }

    @Override
    public UpdateUserResponse updateUser(Long id, UpdateUserRequest request) {
        var user = userRepository.findUserById(id)
                .orElseThrow(
                        () -> new UserNotFoundException(
                                "User not found! ID: ", id.toString(), debugId));
        modelMapper.map(request, user);
        return modelMapper.map(userRepository.save(user),
                UpdateUserResponse.class);
    }

    @Override
    public UserResponse removeUserById(Long id) {
        var user = userRepository.findUserById(id)
                .orElseThrow(
                        () -> new UserNotFoundException(
                                "User not found! ID: ", id.toString(), debugId));
        userRepository.deleteById(id);
        return modelMapper.map(user, UserResponse.class);
    }
}
