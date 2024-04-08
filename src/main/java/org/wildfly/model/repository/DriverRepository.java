package org.wildfly.model.repository;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.wildfly.model.entity.Driver;

import java.util.List;
import java.util.Optional;


@Stateless
public class DriverRepository {
    @Inject
    private EntityManager em;

    public Driver create(Driver driver) {
        em.persist(driver);

        return driver;
    }
    public List<Driver> findAll() {
        return em.createNamedQuery("Driver.findAll", Driver.class).getResultList();
    }

    public Optional<Driver> findById(Long id) {
        return Optional.ofNullable(em.find(Driver.class, id));
    }

    public void delete(Long id) {
        Driver driver = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid driver Id:" + id));
        em.remove(driver);
    }

    public Driver update(Driver driver) {
        return em.merge(driver);
    }
}
