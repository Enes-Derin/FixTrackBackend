package com.enesderin.FixTrackBackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceFormRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private Long customerId;

    private String customerSignature;

    private String technicianSignature;

    // Yeni Alanlar
    private String machineType;           // Makine Tipi
    private String machineSerialNumber;   // Makine Seri Numarası
    private Integer workingHours;         // Çalışma Saati
    private List<String> usedParts;       // Kullanılan Parçalar
    private List<Integer> usedPartQuantities; // Kullanılan Parça Adetleri
}