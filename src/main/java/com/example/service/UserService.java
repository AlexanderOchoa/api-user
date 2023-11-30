package com.example.service;

import com.example.request.SaveUserRequest;
import com.example.response.SaveUserResponse;

public interface UserService {
    SaveUserResponse register(SaveUserRequest request);
}
