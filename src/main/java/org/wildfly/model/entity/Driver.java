package org.wildfly.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@NamedQuery(name = "Driver.findAll", query = "SELECT d FROM Driver d")
@Data
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name is required.")
    @NotEmpty(message = "Name cannot be empty.")
    private String name;

    @NotNull(message = "License number is required.")
    @NotEmpty(message = "License number cannot be empty.")
    private String licenseNo;

    public Driver(String name, String licenseNo) {
        this.name = name;
        this.licenseNo = licenseNo;
    }

    public Driver() {

    }
}
