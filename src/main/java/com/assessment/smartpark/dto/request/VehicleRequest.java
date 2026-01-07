package com.assessment.smartpark.dto.request;

import com.assessment.smartpark.entity.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleRequest {
    @NotBlank(message = "License plate is required")
    @Pattern(regexp = "^[A-Za-z0-9-]+$", message = "License plate can only contain letters, numbers, and dashes")
    private String licensePlate;

    @NotNull(message = "Vehicle type is required")
    private VehicleType type;

    @NotBlank(message = "Owner name is required")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Owner name can only contain letters and spaces")
    private String ownerName;
}
