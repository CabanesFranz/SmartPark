package com.assessment.smartpark.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "parking_lots")
@Getter
@Setter
public class ParkingLot {

    @Id
    @Size(max = 50, message = "Lot ID cannot exceed 50 characters")
    @NotBlank(message = "Lot ID is required")
    @Column(name = "lot_id", length = 50)
    private String lotId;

    @NotBlank(message = "Location is required")
    @Column(nullable = false)
    private String location;

    @Min(value = 1, message = "Capacity must be at least 1")
    @Column(nullable = false)
    private Integer capacity;

    @Min(value = 0, message = "Occupied spaces cannot be negative")
    @Column(nullable = false)
    private Integer occupiedSpaces = 0;

    @OneToMany(mappedBy = "currentLot", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Vehicle> parkedVehicles = new HashSet<>();

    public ParkingLot() {
    }

    public Integer getAvailableSpaces() {
        return capacity - occupiedSpaces;
    }

    public boolean isFull() {
        return occupiedSpaces >= capacity;
    }

    public ParkingLot(String lotId, String location, Integer capacity) {
        this.lotId = lotId;
        this.location = location;
        this.capacity = capacity;
        this.occupiedSpaces = 0;
    }

}
