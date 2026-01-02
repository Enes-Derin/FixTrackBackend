package com.enesderin.FixTrackBackend.dto.response;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ServiceFormResponse {
    private Long id;
    private String title;
    private String description;
    private Date createdDate;
    private Long customerId;
    private String customerSignatureUrl;
    private String technicianSignatureUrl;
    private CustomerResponse customer;

    private String machineType;
    private String machineSerialNumber;
    private Integer workingHours;
    private List<String> usedParts;
    private List<Integer> usedPartQuantities;
}