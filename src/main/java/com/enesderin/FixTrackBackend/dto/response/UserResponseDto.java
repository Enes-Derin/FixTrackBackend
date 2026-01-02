package com.enesderin.FixTrackBackend.dto.response;

import com.enesderin.FixTrackBackend.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Role role;
}
