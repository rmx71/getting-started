package org.wildfly.service;

import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.wildfly.examples.Resources;
import org.wildfly.exception.ResourceNotFoundException;
import org.wildfly.model.entity.Driver;
import org.wildfly.model.entity.Passenger;
import org.wildfly.model.entity.TaxiRide;
import org.wildfly.model.repository.DriverRepository;
import org.wildfly.model.repository.PassengerRepository;
import org.wildfly.model.repository.TaxiRideRepository;
import org.wildfly.validator.CustomValidator;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ArquillianExtension.class)
public class TaxiRideServiceIT {

    @Inject
    private TaxiRideService service;

    @Inject
    private DriverService driverService;

    @Inject
    private PassengerService passengerService;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClasses(TaxiRide.class, TaxiRideService.class, TaxiRideRepository.class, CustomValidator.class,
                        Resources.class, ResourceNotFoundException.class, DriverService.class, DriverRepository.class,
                        PassengerRepository.class, PassengerService.class, Passenger.class, Driver.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsResource("META-INF/initial-data.sql");
    }

    @Test
    public void testCreate() {

        createTaxiRideRecord();
        assertEquals(1, service.findAll().size());
    }

    private void createTaxiRideRecord() {
        Driver driver = driverService.findById(1L).get();
        Passenger passenger = passengerService.findById(1L).get();

        TaxiRide taxiRide = new TaxiRide();
        taxiRide.setDriver(driver);
        taxiRide.setPassengers(Set.of(passenger));
        taxiRide.setCost(100);
        taxiRide.setDuration(20);
        service.create(taxiRide);
    }

    @Test
    public void testFindAll() {
        assertEquals(0, service.findAll().size());

        createTaxiRideRecord();
        assertEquals(1, service.findAll().size());
    }

    @Test
    public void testFindById() {
        createTaxiRideRecord();
        TaxiRide taxiRide = service.findById(1L).get();
        assertEquals(1L, taxiRide.getDriver().getId());
    }

    @Test
    public void testDelete() {
        createTaxiRideRecord();
        assertEquals(1, service.findAll().size());

        service.delete(1L);
        assertEquals(0, service.findAll().size());
    }

    @Test
    public void deletePassenger() {
        createTaxiRideRecord();
        assertEquals(1, service.findAll().size());

        service.deletePassenger(1, 1);
        assertTrue(service.findById(1L).get().getPassengers().isEmpty());
    }
}
