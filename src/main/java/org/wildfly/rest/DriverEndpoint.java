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
import org.wildfly.model.entity.Driver;
import org.wildfly.service.DriverService;
import org.wildfly.validator.CustomValidator;

import java.util.List;

@Path("drivers")
public class DriverEndpoint {
    @Inject
    private DriverService driverService;

    @Inject
    private CustomValidator validator;

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Driver findDriver(@PathParam("id") Long id) {
        return driverService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Driver not found."));
    }

    @GET
    @Produces("application/json")
    public List<Driver> findAll() {
        return driverService.findAll();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Driver create(Driver driver) {
        validator.validate(driver);
        return driverService.create(driver);
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        driverService.delete(id);
    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Driver update(Driver driver) {
        return driverService.update(driver);
    }
}
