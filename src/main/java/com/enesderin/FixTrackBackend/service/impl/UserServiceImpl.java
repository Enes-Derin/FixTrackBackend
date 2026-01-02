package com.enesderin.FixTrackBackend.service.impl;

import com.enesderin.FixTrackBackend.dto.request.UserRequestDto;
import com.enesderin.FixTrackBackend.dto.response.UserResponseDto;
import com.enesderin.FixTrackBackend.exception.ErrorMessage;
import com.enesderin.FixTrackBackend.exception.MessageType;
import com.enesderin.FixTrackBackend.exception.handler.BaseException;
import com.enesderin.FixTrackBackend.model.Role;
import com.enesderin.FixTrackBackend.model.User;
import com.enesderin.FixTrackBackend.repository.UserRepository;
import com.enesderin.FixTrackBackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto getUserById(Long id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (optionalUser.isPresent()) {
            UserResponseDto user = new UserResponseDto();
            user.setId(id);
            user.setUsername(optionalUser.get().getUsername());
            user.setPassword(optionalUser.get().getPassword());
            user.setEmail(optionalUser.get().getEmail());
            user.setRole(optionalUser.get().getRole());
            return user;
        }
        throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "User with id " + id + " not found"));
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<UserResponseDto> userResponseDtoList = new ArrayList<UserResponseDto>();
        for (User user : this.userRepository.findAll()) {
            UserResponseDto userResponseDto = new UserResponseDto();
            userResponseDto.setId(user.getId());
            userResponseDto.setUsername(user.getUsername());
            userResponseDto.setPassword(user.getPassword());
            userResponseDto.setEmail(user.getEmail());
            userResponseDto.setRole(user.getRole());
            userResponseDtoList.add(userResponseDto);
        }
        return userResponseDtoList;
    }

    @Override
    public UserResponseDto addUser(UserRequestDto user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setEmail(user.getEmail());
        newUser.setRole(Role.ADMIN);
        this.userRepository.save(newUser);
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(newUser.getId());
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setPassword(user.getPassword());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setRole(user.getRole());
        return userResponseDto;

    }

    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto user) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User newUser = new User();
            newUser.setUsername(user.getUsername());
            newUser.setPassword(user.getPassword());
            newUser.setEmail(user.getEmail());
            newUser.setRole(user.getRole());
            this.userRepository.save(newUser);
            UserResponseDto userResponseDto = new UserResponseDto();
            userResponseDto.setId(id);
            userResponseDto.setUsername(user.getUsername());
            userResponseDto.setPassword(user.getPassword());
            userResponseDto.setEmail(user.getEmail());
            userResponseDto.setRole(Role.ADMIN);
            return userResponseDto;
        }
        return null;
    }

    @Override
    public String deleteUser(Long id) {
        this.userRepository.deleteById(id);
        return "User deleted";
    }
}
