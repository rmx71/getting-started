package org.wildfly.service;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.wildfly.exception.ResourceNotFoundException;
import org.wildfly.model.entity.Passenger;
import org.wildfly.model.repository.PassengerRepository;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Stateless
public class PassengerService {

    @Inject
    private Logger logger;
    @Inject
    PassengerRepository passengerRepository;

    public Passenger create(Passenger passenger) {
        logger.info("Creating passenger " + passenger.getFirstName() + " " + passenger.getLastName());
        return passengerRepository.create(passenger);
    }

    public List<Passenger> findAll() {
        logger.info("Getting all passengers");
        return passengerRepository.findAll();
    }

    public Optional<Passenger> findById(Long id) {
        logger.info("Getting passenger by id " + id);
        return passengerRepository.findById(id);
    }

    public void delete(long id) {
        logger.info("Deleting passenger by id " + id);
        passengerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Passenger not found."));
        passengerRepository.delete(id);
    }

    public Passenger update(Passenger passenger) {
        logger.info("Creating passenger " + passenger.getFirstName() + " " + passenger.getLastName());
        passengerRepository.findById(passenger.getId()).orElseThrow(() -> new ResourceNotFoundException("Passenger not found."));
        return passengerRepository.update(passenger);
    }
}
