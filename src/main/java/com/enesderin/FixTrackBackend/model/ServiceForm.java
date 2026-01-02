package com.enesderin.FixTrackBackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class ServiceForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "customer_signature_url", nullable = false)
    @Size(max = 255, message = "Customer signature URL length must not exceed 255 characters")
    private String customerSignatureUrl;

    @Column(name = "technician_signature_url", nullable = false)
    @Size(max = 255, message = "Technician signature URL length must not exceed 255 characters")
    private String technicianSignatureUrl;


    private String machineType;
    private String machineSerialNumber;
    private Integer workingHours;

    @ElementCollection
    private List<String> usedParts;

    @ElementCollection
    private List<Integer> usedPartQuantities;
}