package com.enesderin.FixTrackBackend.controller.impl;


import com.enesderin.FixTrackBackend.controller.RestBaseController;
import com.enesderin.FixTrackBackend.controller.RootEntity;
import com.enesderin.FixTrackBackend.controller.UserCntroller;
import com.enesderin.FixTrackBackend.dto.request.UserRequestDto;
import com.enesderin.FixTrackBackend.dto.response.UserResponseDto;
import com.enesderin.FixTrackBackend.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserControllerImpl extends RestBaseController implements UserCntroller {

    private UserService userService;

    @GetMapping("/{id}")
    @Override
    public RootEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        return success(userService.getUserById(id));
    }

    @GetMapping
    @Override
    public RootEntity<List<UserResponseDto>> getUsers() {
        return success(userService.getAllUsers());
    }

    @PostMapping
    @Override
    public RootEntity<UserResponseDto> addUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        return success(userService.addUser(userRequestDto));
    }

    @PutMapping("/update/{id}")
    @Override
    public RootEntity<UserResponseDto> updateUser(@PathVariable Long id,@Valid @RequestBody UserRequestDto userRequestDto) {
        return success(userService.updateUser(id,userRequestDto));
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public RootEntity<String> deleteUser(@PathVariable Long id) {
        return success(userService.deleteUser(id));
    }
}
