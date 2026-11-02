package com.newlearn.expenses_api.mapper;

import com.newlearn.expenses_api.dto.request.UserRequest;
import com.newlearn.expenses_api.dto.response.UserResponse;
import com.newlearn.expenses_api.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequest request) {

        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }

    public UserResponse toResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public void updateEntity(User user, UserRequest request) {

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
    }
}