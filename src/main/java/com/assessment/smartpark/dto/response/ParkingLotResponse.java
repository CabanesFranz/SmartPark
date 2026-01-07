package com.assessment.smartpark.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParkingLotResponse {

    private String lotId;
    private String location;
    private Integer capacity;
    private Integer occupiedSpaces;
    private Integer availableSpaces;

    public ParkingLotResponse(String lotId, String location, Integer capacity,
                              Integer occupiedSpaces, Integer availableSpaces) {
        this.lotId = lotId;
        this.location = location;
        this.capacity = capacity;
        this.occupiedSpaces = occupiedSpaces;
        this.availableSpaces = availableSpaces;
    }
}
