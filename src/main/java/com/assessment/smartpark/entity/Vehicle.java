package com.assessment.smartpark.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
public class Vehicle {

    @Id
    @Pattern(regexp = "^[A-Za-z0-9-]+$", message = "License plate can only contain letters, numbers, and dashes")
    @NotBlank(message = "License plate is required")
    @Column(name = "license_plate", length = 50)
    private String licensePlate;

    @NotNull(message = "Vehicle type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType type;

    @Pattern(regexp = "^[A-Za-z ]+$", message = "Owner name can only contain letters and spaces")
    @NotBlank(message = "Owner name is required")
    @Column(nullable = false)
    private String ownerName;

    @ManyToOne
    @JoinColumn(name = "current_lot_id")
    private ParkingLot currentLot;

    public Vehicle(String licensePlate, VehicleType type, String ownerName) {
        this.licensePlate = licensePlate;
        this.type = type;
        this.ownerName = ownerName;
    }

    public Vehicle() {
    }
}
