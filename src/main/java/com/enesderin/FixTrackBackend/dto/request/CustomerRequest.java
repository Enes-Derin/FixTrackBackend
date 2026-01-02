package com.enesderin.FixTrackBackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String company;
    @NotBlank
    private String address;
    @NotBlank
    private String phone;
    @NotBlank
    private String email;
}
