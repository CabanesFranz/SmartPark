package com.assessment.smartpark.dto.response;

import com.assessment.smartpark.entity.VehicleType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleResponse {

    private String licensePlate;
    private VehicleType type;
    private String ownerName;
    private String currentLotId;

    public VehicleResponse(String licensePlate, VehicleType type, String ownerName, String currentLotId) {
        this.licensePlate = licensePlate;
        this.type = type;
        this.ownerName = ownerName;
        this.currentLotId = currentLotId;
    }
}
