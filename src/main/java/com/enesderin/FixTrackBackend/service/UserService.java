package com.enesderin.FixTrackBackend.service;

import com.enesderin.FixTrackBackend.dto.request.UserRequestDto;
import com.enesderin.FixTrackBackend.dto.response.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto getUserById(Long id);
    List<UserResponseDto> getAllUsers();
    UserResponseDto addUser(UserRequestDto user);
    UserResponseDto updateUser(Long id, UserRequestDto user);
    String deleteUser(Long id);
}
