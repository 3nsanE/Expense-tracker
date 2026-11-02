package com.newlearn.expenses_api.serviceImpl;

import com.newlearn.expenses_api.dto.request.UserRequest;
import com.newlearn.expenses_api.dto.response.UserResponse;
import com.newlearn.expenses_api.entity.User;
import com.newlearn.expenses_api.exception.DuplicateResourceException;
import com.newlearn.expenses_api.exception.ResourceNotFoundException;
import com.newlearn.expenses_api.mapper.UserMapper;
import com.newlearn.expenses_api.repository.UserRepository;
import com.newlearn.expenses_api.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse createUser(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists"+request.getEmail());
        }
        User user = userMapper.toEntity(request);
        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    public UserResponse getUserById(Long id) {
        User user=userRepository.findById(id).
                orElseThrow(() ->
                new ResourceNotFoundException("User not found with id: "+id));
        return userMapper.toResponse(user);
    }

    @Override
    @Transactional
    public UserResponse getUserByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with email: " + email));

        return userMapper.toResponse(user);
    }

    @Override
    @Transactional
    public UserResponse updateUser(Long id, UserRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        if (!user.getEmail().equals(request.getEmail())
                && userRepository.existsByEmail(request.getEmail())) {

            throw new DuplicateResourceException("Email already exists"+request.getEmail());
        }

        userMapper.updateEntity(user, request);

        User updatedUser = userRepository.save(user);

        return userMapper.toResponse(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        userRepository.delete(user);
    }
}
