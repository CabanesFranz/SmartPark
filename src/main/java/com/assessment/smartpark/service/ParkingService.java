package com.assessment.smartpark.service;

import com.assessment.smartpark.entity.ParkingLot;
import com.assessment.smartpark.entity.Vehicle;
import com.assessment.smartpark.exception.BusinessException;
import com.assessment.smartpark.repository.ParkingLotRepository;
import com.assessment.smartpark.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingService {

    private final ParkingLotRepository parkingLotRepository;
    private  final VehicleRepository vehicleRepository;

    @Transactional
    public ParkingLot registerParkingLot(ParkingLot parkingLot) {
        if (parkingLotRepository.existsById(parkingLot.getLotId())) {
            throw new BusinessException("Parking lot with ID " + parkingLot.getLotId() + " already exists");
        }
        return parkingLotRepository.save(parkingLot);
    }

    @Transactional
    public Vehicle registerVehicle(Vehicle vehicle) {
        if (vehicleRepository.existsById(vehicle.getLicensePlate())) {
            throw new BusinessException("Vehicle with license plate " + vehicle.getLicensePlate() + " already exists");
        }
        return vehicleRepository.save(vehicle);
    }

    @Transactional
    public void checkIn(String licensePlate, String lotId) {
        Vehicle vehicle = vehicleRepository.findById(licensePlate)
                .orElseThrow(() -> new BusinessException("Vehicle with license plate " + licensePlate + " not found"));

        ParkingLot parkingLot = parkingLotRepository.findById(lotId)
                .orElseThrow(() -> new BusinessException("Parking lot with ID " + lotId + " not found"));

        if (vehicle.getCurrentLot() != null) {
            throw new BusinessException("Vehicle is already parked in lot: " + vehicle.getCurrentLot().getLotId());
        }

        if (parkingLot.isFull()) {
            throw new BusinessException("Parking lot " + lotId + " is full");
        }

        vehicle.setCurrentLot(parkingLot);
        parkingLot.setOccupiedSpaces(parkingLot.getOccupiedSpaces() + 1);

        vehicleRepository.save(vehicle);
        parkingLotRepository.save(parkingLot);
    }

    @Transactional
    public void checkOut(String licensePlate) {
        Vehicle vehicle = vehicleRepository.findById(licensePlate)
                .orElseThrow(() -> new BusinessException("Vehicle with license plate " + licensePlate + " not found"));

        if (vehicle.getCurrentLot() == null) {
            throw new BusinessException("Vehicle is not currently parked in any lot");
        }

        ParkingLot parkingLot = vehicle.getCurrentLot();

        vehicle.setCurrentLot(null);
        parkingLot.setOccupiedSpaces(parkingLot.getOccupiedSpaces() - 1);

        vehicleRepository.save(vehicle);
        parkingLotRepository.save(parkingLot);
    }

    public ParkingLot getParkingLotOccupancy(String lotId) {
        return parkingLotRepository.findById(lotId)
                .orElseThrow(() -> new BusinessException("Parking lot with ID " + lotId + " not found"));
    }

    public List<Vehicle> getVehiclesInLot(String lotId) {
        ParkingLot parkingLot = parkingLotRepository.findById(lotId)
                .orElseThrow(() -> new BusinessException("Parking lot with ID " + lotId + " not found"));

        return vehicleRepository.findByCurrentLot(parkingLot);
    }

    public List<ParkingLot> getAllParkingLots() {
        return parkingLotRepository.findAll();
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }
}
