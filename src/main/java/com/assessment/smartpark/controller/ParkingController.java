package com.assessment.smartpark.controller;

import com.assessment.smartpark.dto.request.CheckInRequest;
import com.assessment.smartpark.dto.request.ParkingLotRequest;
import com.assessment.smartpark.dto.request.VehicleRequest;
import com.assessment.smartpark.dto.response.ParkingLotResponse;
import com.assessment.smartpark.dto.response.VehicleResponse;
import com.assessment.smartpark.entity.ParkingLot;
import com.assessment.smartpark.entity.Vehicle;
import com.assessment.smartpark.service.ParkingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ParkingController {


    private final ParkingService parkingService;

    @PostMapping("/parking-lots")
    public ResponseEntity<ParkingLotResponse> registerParkingLot(@Valid @RequestBody ParkingLotRequest request) {
        ParkingLot parkingLot = new ParkingLot(
                request.getLotId(),
                request.getLocation(),
                request.getCapacity()
        );

        ParkingLot saved = parkingService.registerParkingLot(parkingLot);
        return ResponseEntity.status(HttpStatus.CREATED).body(toParkingLotResponse(saved));
    }

    @PostMapping("/vehicles")
    public ResponseEntity<VehicleResponse> registerVehicle(@Valid @RequestBody VehicleRequest request) {
        Vehicle vehicle = new Vehicle(
                request.getLicensePlate(),
                request.getType(),
                request.getOwnerName()
        );

        Vehicle saved = parkingService.registerVehicle(vehicle);
        return ResponseEntity.status(HttpStatus.CREATED).body(toVehicleResponse(saved));
    }

    @PostMapping("/check-in")
    public ResponseEntity<String> checkIn(@Valid @RequestBody CheckInRequest request) {
        parkingService.checkIn(request.getLicensePlate(), request.getLotId());
        return ResponseEntity.ok("Vehicle " + request.getLicensePlate() + " checked in to lot " + request.getLotId());
    }

    @PostMapping("/check-out/{licensePlate}")
    public ResponseEntity<String> checkOut(@PathVariable String licensePlate) {
        parkingService.checkOut(licensePlate);
        return ResponseEntity.ok("Vehicle " + licensePlate + " checked out successfully");
    }

    @GetMapping("/parking-lots/{lotId}/occupancy")
    public ResponseEntity<ParkingLotResponse> getParkingLotOccupancy(@PathVariable String lotId) {
        ParkingLot parkingLot = parkingService.getParkingLotOccupancy(lotId);
        return ResponseEntity.ok(toParkingLotResponse(parkingLot));
    }

    @GetMapping("/parking-lots/{lotId}/vehicles")
    public ResponseEntity<List<VehicleResponse>> getVehiclesInLot(@PathVariable String lotId) {
        List<Vehicle> vehicles = parkingService.getVehiclesInLot(lotId);
        List<VehicleResponse> response = vehicles.stream()
                .map(this::toVehicleResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/parking-lots")
    public ResponseEntity<List<ParkingLotResponse>> getAllParkingLots() {
        List<ParkingLot> lots = parkingService.getAllParkingLots();
        List<ParkingLotResponse> response = lots.stream()
                .map(this::toParkingLotResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/vehicles")
    public ResponseEntity<List<VehicleResponse>> getAllVehicles() {
        List<Vehicle> vehicles = parkingService.getAllVehicles();
        List<VehicleResponse> response = vehicles.stream()
                .map(this::toVehicleResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    private ParkingLotResponse toParkingLotResponse(ParkingLot lot) {
        return new ParkingLotResponse(
                lot.getLotId(),
                lot.getLocation(),
                lot.getCapacity(),
                lot.getOccupiedSpaces(),
                lot.getAvailableSpaces()
        );
    }

    private VehicleResponse toVehicleResponse(Vehicle vehicle) {
        String currentLotId = vehicle.getCurrentLot() != null ?
                vehicle.getCurrentLot().getLotId() : null;
        return new VehicleResponse(
                vehicle.getLicensePlate(),
                vehicle.getType(),
                vehicle.getOwnerName(),
                currentLotId
        );
    }
}