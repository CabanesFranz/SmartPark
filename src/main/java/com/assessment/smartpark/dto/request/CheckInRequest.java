package com.assessment.smartpark.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckInRequest {
    @NotBlank(message = "License plate is required")
    private String licensePlate;

    @NotBlank(message = "Lot ID is required")
    private String lotId;
}
