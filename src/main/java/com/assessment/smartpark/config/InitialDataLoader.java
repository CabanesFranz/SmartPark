package com.assessment.smartpark.config;

import com.assessment.smartpark.entity.ParkingLot;
import com.assessment.smartpark.entity.Vehicle;
import com.assessment.smartpark.entity.VehicleType;
import com.assessment.smartpark.repository.ParkingLotRepository;
import com.assessment.smartpark.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitialDataLoader implements CommandLineRunner {

    private final ParkingLotRepository parkingLotRepository;
    private final VehicleRepository vehicleRepository;

    @Override
    public void run(String... args) throws Exception {
        ParkingLot lot1 = new ParkingLot("LOT-001", "Downtown Mall", 50);
        ParkingLot lot2 = new ParkingLot("LOT-002", "City Center", 100);
        ParkingLot lot3 = new ParkingLot("LOT-003", "Airport Terminal", 200);
        ParkingLot lot4 = new ParkingLot("LOT-004", "Shopping District", 75);

        parkingLotRepository.save(lot1);
        parkingLotRepository.save(lot2);
        parkingLotRepository.save(lot3);
        parkingLotRepository.save(lot4);

        Vehicle v1 = new Vehicle("ABC-123", VehicleType.CAR, "John Doe");
        Vehicle v2 = new Vehicle("XYZ-789", VehicleType.MOTORCYCLE, "Jane Smith");
        Vehicle v3 = new Vehicle("TRK-456", VehicleType.TRUCK, "Bob Johnson");
        Vehicle v4 = new Vehicle("CAR-001", VehicleType.CAR, "Alice Williams");
        Vehicle v5 = new Vehicle("MOTO-99", VehicleType.MOTORCYCLE, "Charlie Brown");

        vehicleRepository.save(v1);
        vehicleRepository.save(v2);
        vehicleRepository.save(v3);
        vehicleRepository.save(v4);
        vehicleRepository.save(v5);

        System.out.println("Sample data loaded successfully!");
    }
}