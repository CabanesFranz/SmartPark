package com.assessment.smartpark.service;

import com.assessment.smartpark.entity.ParkingLot;
import com.assessment.smartpark.entity.Vehicle;
import com.assessment.smartpark.entity.VehicleType;
import com.assessment.smartpark.exception.BusinessException;
import com.assessment.smartpark.repository.ParkingLotRepository;
import com.assessment.smartpark.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ParkingServiceTest {

    private final ParkingService parkingService;
    private final ParkingLotRepository parkingLotRepository;
    private final VehicleRepository vehicleRepository;

    @Autowired
    public ParkingServiceTest(ParkingService parkingService,
                              ParkingLotRepository parkingLotRepository,
                              VehicleRepository vehicleRepository) {
        this.parkingService = parkingService;
        this.parkingLotRepository = parkingLotRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @BeforeEach
    public void setUp() {
        vehicleRepository.deleteAll();
        parkingLotRepository.deleteAll();
    }

    @Test
    public void testRegisterParkingLot() {
        ParkingLot lot = new ParkingLot("TEST-001", "Test Location", 10);
        ParkingLot saved = parkingService.registerParkingLot(lot);

        assertNotNull(saved);
        assertEquals("TEST-001", saved.getLotId());
        assertEquals(10, saved.getCapacity());
        assertEquals(0, saved.getOccupiedSpaces());
    }

    @Test
    public void testRegisterDuplicateParkingLot() {
        ParkingLot lot1 = new ParkingLot("TEST-001", "Test Location", 10);
        parkingService.registerParkingLot(lot1);

        ParkingLot lot2 = new ParkingLot("TEST-001", "Another Location", 20);
        assertThrows(BusinessException.class, () -> parkingService.registerParkingLot(lot2));
    }

    @Test
    public void testRegisterVehicle() {
        Vehicle vehicle = new Vehicle("ABC-123", VehicleType.CAR, "John Doe");
        Vehicle saved = parkingService.registerVehicle(vehicle);

        assertNotNull(saved);
        assertEquals("ABC-123", saved.getLicensePlate());
        assertEquals(VehicleType.CAR, saved.getType());
    }

    @Test
    public void testCheckInVehicle() {
        ParkingLot lot = new ParkingLot("TEST-001", "Test Location", 10);
        parkingService.registerParkingLot(lot);

        Vehicle vehicle = new Vehicle("ABC-123", VehicleType.CAR, "John Doe");
        parkingService.registerVehicle(vehicle);

        parkingService.checkIn("ABC-123", "TEST-001");

        ParkingLot updatedLot = parkingLotRepository.findById("TEST-001").get();
        Vehicle updatedVehicle = vehicleRepository.findById("ABC-123").get();

        assertEquals(1, updatedLot.getOccupiedSpaces());
        assertNotNull(updatedVehicle.getCurrentLot());
        assertEquals("TEST-001", updatedVehicle.getCurrentLot().getLotId());
    }

    @Test
    public void testCheckInToFullParkingLot() {
        ParkingLot lot = new ParkingLot("TEST-001", "Test Location", 1);
        parkingService.registerParkingLot(lot);

        Vehicle v1 = new Vehicle("ABC-123", VehicleType.CAR, "John Doe");
        Vehicle v2 = new Vehicle("XYZ-789", VehicleType.CAR, "Jane Smith");
        parkingService.registerVehicle(v1);
        parkingService.registerVehicle(v2);

        parkingService.checkIn("ABC-123", "TEST-001");

        assertThrows(BusinessException.class, () -> parkingService.checkIn("XYZ-789", "TEST-001"));
    }

    @Test
    public void testCheckInAlreadyParkedVehicle() {
        ParkingLot lot1 = new ParkingLot("TEST-001", "Test Location 1", 10);
        ParkingLot lot2 = new ParkingLot("TEST-002", "Test Location 2", 10);
        parkingService.registerParkingLot(lot1);
        parkingService.registerParkingLot(lot2);

        Vehicle vehicle = new Vehicle("ABC-123", VehicleType.CAR, "John Doe");
        parkingService.registerVehicle(vehicle);

        parkingService.checkIn("ABC-123", "TEST-001");

        assertThrows(BusinessException.class, () -> parkingService.checkIn("ABC-123", "TEST-002"));
    }

    @Test
    public void testCheckOutVehicle() {
        ParkingLot lot = new ParkingLot("TEST-001", "Test Location", 10);
        parkingService.registerParkingLot(lot);

        Vehicle vehicle = new Vehicle("ABC-123", VehicleType.CAR, "John Doe");
        parkingService.registerVehicle(vehicle);

        parkingService.checkIn("ABC-123", "TEST-001");
        parkingService.checkOut("ABC-123");

        ParkingLot updatedLot = parkingLotRepository.findById("TEST-001").get();
        Vehicle updatedVehicle = vehicleRepository.findById("ABC-123").get();

        assertEquals(0, updatedLot.getOccupiedSpaces());
        assertNull(updatedVehicle.getCurrentLot());
    }

    @Test
    public void testCheckOutNotParkedVehicle() {
        Vehicle vehicle = new Vehicle("ABC-123", VehicleType.CAR, "John Doe");
        parkingService.registerVehicle(vehicle);

        assertThrows(BusinessException.class, () -> parkingService.checkOut("ABC-123"));
    }

    @Test
    public void testGetVehiclesInLot() {
        ParkingLot lot = new ParkingLot("TEST-001", "Test Location", 10);
        parkingService.registerParkingLot(lot);

        Vehicle v1 = new Vehicle("ABC-123", VehicleType.CAR, "John Doe");
        Vehicle v2 = new Vehicle("XYZ-789", VehicleType.MOTORCYCLE, "Jane Smith");
        parkingService.registerVehicle(v1);
        parkingService.registerVehicle(v2);

        parkingService.checkIn("ABC-123", "TEST-001");
        parkingService.checkIn("XYZ-789", "TEST-001");

        assertEquals(2, parkingService.getVehiclesInLot("TEST-001").size());
    }
}