package com.example.mongorestapi.controller;

import com.example.mongorestapi.dto.request.UpdateUserRequest;
import com.example.mongorestapi.dto.request.UserRequest;
import com.example.mongorestapi.dto.response.UpdateUserResponse;
import com.example.mongorestapi.dto.response.UserResponse;
import com.example.mongorestapi.exception.ErrorMessage;
import com.example.mongorestapi.exception.UserNotFoundException;
import com.example.mongorestapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@RestController
@RequestScope
@RequestMapping("/users")
@CrossOrigin
@Validated
public class UserRestController {
    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public UserResponse createUser(@RequestBody @Validated UserRequest user) {
        return userService.createUser(user);
    }

    @DeleteMapping("/{id}")
    public UserResponse removeUserById(@PathVariable Long id) {
        return userService.removeUserById(id);
    }

    @GetMapping("/{id}")
    public UserResponse findUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @GetMapping
    public List<UserResponse> getUsersByPage(
            @RequestParam(required = false, defaultValue = "0")
                    int pageNo,
            @RequestParam(required = false, defaultValue = "20")
                    int pageSize) {
        return userService.findAllUser(pageNo, pageSize);
    }

    @PutMapping(value = "/{id}")

    public UpdateUserResponse updateUser(@PathVariable Long id,
                                         @RequestBody @Validated UpdateUserRequest request) {
        return userService.updateUser(id, request);

    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleUserNotFoundException(
            UserNotFoundException e) {
        return new ErrorMessage(
                e.getMessage(),
                e.getI18nId(),
                e.getDebugId());
    }

}
