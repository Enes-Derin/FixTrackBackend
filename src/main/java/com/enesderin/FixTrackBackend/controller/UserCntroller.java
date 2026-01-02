package com.enesderin.FixTrackBackend.controller;


import com.enesderin.FixTrackBackend.dto.request.UserRequestDto;
import com.enesderin.FixTrackBackend.dto.response.UserResponseDto;

import java.util.List;

public interface UserCntroller {

    RootEntity<UserResponseDto> getUserById(Long id);
    RootEntity<List<UserResponseDto>> getUsers();
    RootEntity<UserResponseDto> addUser(UserRequestDto userRequestDto);
    RootEntity<UserResponseDto> updateUser(Long id , UserRequestDto userRequestDto);
    RootEntity<String> deleteUser(Long id);
}
