package com.newlearn.expenses_api.service;

import com.newlearn.expenses_api.dto.request.UserRequest;
import com.newlearn.expenses_api.dto.response.UserResponse;

public interface UserService {
    UserResponse createUser(UserRequest request);
    UserResponse getUserById(Long id);
    UserResponse getUserByEmail(String email);
    UserResponse updateUser(Long id, UserRequest request);

    void deleteUser(Long id);
}
