package com.enesderin.FixTrackBackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSignatureRequest {
    private String customerSignature;
    private String technicianSignature;
}

