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
import org.wildfly.model.repository.DriverRepository;
import org.wildfly.validator.CustomValidator;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Run integration tests with Arquillian to be able to test CDI beans
 */
@ExtendWith(ArquillianExtension.class)
public class DriverServiceIT {
    @Inject
    DriverService service;


    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClasses(Driver.class, DriverService.class, DriverRepository.class, CustomValidator.class, Resources.class, ResourceNotFoundException.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsResource("META-INF/initial-data.sql");
    }

    @Test
    public void testCreate() {
        Driver driver = new Driver();
        driver.setName("Derrick Rose");
        driver.setLicenseNo("DR-01");
        service.create(driver);

        assertEquals(3, service.findAll().size());
        assertTrue(service.findAll().stream().anyMatch(d -> d.getName().equals("Derrick Rose")));
    }

    @Test
    public void testFindAll() {
        List<Driver> driver = service.findAll();
        assertEquals(2, driver.size());
    }

    @Test
    public void testFindById() {
        Optional<Driver> driver = service.findById(1L);
        assertEquals("Steph Curry", driver.get().getName());
    }



    @Test
    public void delete() {
        service.delete(1L);
        assertEquals(1, service.findAll().size());
    }

    @Test
    public void update() {
        Driver driver = new Driver();
        driver.setId(2L);
        driver.setName("Klay Thompson");
        driver.setLicenseNo("KT-91");
        service.update(driver);

        assertEquals("KT-91", service.findById(2L).get().getLicenseNo());
    }
}