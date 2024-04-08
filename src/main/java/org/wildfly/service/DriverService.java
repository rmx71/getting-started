package org.wildfly.service;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.wildfly.exception.ResourceNotFoundException;
import org.wildfly.model.entity.Driver;
import org.wildfly.model.repository.DriverRepository;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Stateless
public class DriverService {

    @Inject
    private Logger logger;
    @Inject
    DriverRepository driverRepository;

    public Driver create(Driver driver) {
        logger.info("Creating driver " + driver.getName());
        return driverRepository.create(driver);
    }

    public List<Driver> findAll() {
        logger.info("Getting all driver");
        return driverRepository.findAll();
    }

    public Optional<Driver> findById(Long id) {
        logger.info("Getting driver by id " + id);
        return driverRepository.findById(id);
    }

    public void delete(Long id) {
        logger.info("Deleting driver by id " + id);
        driverRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Driver not found."));
        driverRepository.delete(id);
    }

    public Driver update(Driver driver) {
        logger.info("Updating driver " + driver.getId());
        driverRepository.findById(driver.getId()).orElseThrow(() -> new ResourceNotFoundException("Driver not found."));
        return driverRepository.update(driver);
    }
}
