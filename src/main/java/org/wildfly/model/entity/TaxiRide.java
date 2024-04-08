package org.wildfly.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
public class TaxiRide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private double cost;

    @NotNull
    private int duration;

    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "taxi_ride_passenger",
            joinColumns = @JoinColumn(name = "taxi_ride_id"),
            inverseJoinColumns = @JoinColumn(name = "passenger_id")
    )
    private Set<Passenger> passengers;
}
