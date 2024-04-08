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
import org.wildfly.model.entity.Passenger;
import org.wildfly.model.repository.PassengerRepository;
import org.wildfly.validator.CustomValidator;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ArquillianExtension.class)
public class PassengerServiceIT {

    @Inject
    PassengerService service;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClasses(Passenger.class, PassengerService.class, PassengerRepository.class, CustomValidator.class, Resources.class, ResourceNotFoundException.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsResource("META-INF/initial-data.sql");
    }

    @Test
    public void testCreate() {
        Passenger passenger = new Passenger();
        passenger.setFirstName("Kobe");
        passenger.setLastName("Bryant");
        passenger.setAge(41);
        service.create(passenger);
        assertEquals(3, service.findAll().size());
    }

    @Test
    public void testFindAll() {
        assertEquals(2, service.findAll().size());
    }

    @Test
    public void testFindById() {
        assertEquals("Michael", service.findById(1L).get().getFirstName());
    }

    @Test
    public void testUpdate() {
        Passenger passenger = service.findById(1L).get();
        passenger.setFirstName("Jordan");
        service.update(passenger);
        assertEquals("Jordan", service.findById(1L).get().getFirstName());
    }

    @Test
    public void testDelete() {
        service.delete(1L);
        assertEquals(1, service.findAll().size());
    }
}
