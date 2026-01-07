package com.assessment.smartpark.repository;

import com.assessment.smartpark.entity.ParkingLot;
import com.assessment.smartpark.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {
    List<Vehicle> findByCurrentLot(ParkingLot parkingLot);
}
