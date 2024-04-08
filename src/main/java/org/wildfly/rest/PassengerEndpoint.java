package org.wildfly.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import org.wildfly.exception.ResourceNotFoundException;
import org.wildfly.model.entity.Passenger;
import org.wildfly.service.PassengerService;
import org.wildfly.validator.CustomValidator;

import java.util.List;

@Path("passengers")
public class PassengerEndpoint {

    @Inject
    private PassengerService passengerService;

    @Inject
    private CustomValidator validator;

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Passenger findPassenger(@PathParam("id") Long id) {
        return passengerService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found."));
    }

    @GET
    @Produces("application/json")
    public List<Passenger> findAll() {
        return passengerService.findAll();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Passenger create(Passenger passenger) {
        validator.validate(passenger);
        return passengerService.create(passenger);
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        passengerService.delete(id);
    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Passenger update(Passenger passenger) {
        return passengerService.update(passenger);
    }
}
