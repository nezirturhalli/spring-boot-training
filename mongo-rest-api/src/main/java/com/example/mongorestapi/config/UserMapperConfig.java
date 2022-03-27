package com.example.mongorestapi.config;

import com.example.mongorestapi.domain.User;
import com.example.mongorestapi.dto.request.UpdateUserRequest;
import com.example.mongorestapi.dto.response.UserResponse;
import com.example.mongorestapi.service.SequenceGeneratorService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;

@Configuration
public class UserMapperConfig {


    private static final Converter<User, UserResponse> USER_TO_USER_RESPONSE_CONVERTER =
            context ->
                    new UserResponse(
                            context.getSource().getId(),
                            context.getSource().getFullname(),
                            context.getSource().getEmail(),
                            context.getSource().getPassword()
                    );


    private static final Converter<UpdateUserRequest, User>
            UPDATE_USER_REQUEST_TO_USER_CONVERTER =
            (context) -> {
                var user = context.getDestination();
                user.setFullname(user.getFullname());
                user.setEmail(user.getEmail());
                user.setPassword(user.getPassword());
                return user;
            };


    @Bean("userModelMapper")
    ModelMapper createModelMapper() {
        var modelMapper = new ModelMapper();
        modelMapper.addConverter(USER_TO_USER_RESPONSE_CONVERTER, User.class, UserResponse.class);
        modelMapper.addConverter(UPDATE_USER_REQUEST_TO_USER_CONVERTER, UpdateUserRequest.class, User.class);
        return modelMapper;
    }


}
