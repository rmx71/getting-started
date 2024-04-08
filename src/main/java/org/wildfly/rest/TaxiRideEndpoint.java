package org.wildfly.rest;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.wildfly.exception.InvalidArgumentsException;
import org.wildfly.exception.ResourceNotFoundException;
import org.wildfly.model.entity.TaxiRide;
import org.wildfly.service.DriverService;
import org.wildfly.service.PassengerService;
import org.wildfly.service.TaxiRideService;
import org.wildfly.validator.CustomValidator;

import java.util.List;

@Path("taxi-rides")
public class TaxiRideEndpoint {

    @Inject
    private TaxiRideService taxiRideService;

    @Inject
    private CustomValidator validator;

    @Inject
    DriverService driverService;

    @Inject
    PassengerService passengerService;

    @GET
    @Path("{id}")
    @Produces("application/json")
    public TaxiRide findTaxiRide(@PathParam("id") Long id) {
        return taxiRideService.findById(id)
                .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }

    @GET
    @Path("/all")
    @Produces("application/json")
    public List<TaxiRide> findAll() {
        return taxiRideService.findAll();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Transactional
    public Response create(TaxiRide taxiRide) {
        validator.validate(taxiRide);
        if (taxiRide.getDriver().getId() == null || taxiRide.getPassengers() == null || taxiRide.getPassengers().isEmpty()) {
            throw new InvalidArgumentsException("Driver id and passenger id are required");
        }

        driverService.findById(taxiRide.getDriver().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Driver " + taxiRide.getDriver().getId() + " not found."));
        taxiRide.getPassengers().forEach(passenger -> passengerService.findById(passenger.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Passenger " + passenger.getId() + " not found.")));
        taxiRideService.create(taxiRide);
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        taxiRideService.delete(id);
    }


    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public TaxiRide update(TaxiRide taxiRide) {
        return taxiRideService.update(taxiRide);
    }

    @GET
    @Produces("application/json")
    public List<TaxiRide> findTaxiRideBy(@QueryParam("startDate") String startDate,
                                   @QueryParam("endDate") String endDate,
                                   @QueryParam("minCost") Double minCost,
                                   @QueryParam("maxCost") Double maxCost,
                                   @QueryParam("minDuration") Integer minDuration,
                                   @QueryParam("maxDuration") Integer maxDuration,
                                   @QueryParam("byDriver") Long driverId,
                                   @QueryParam("byPassenger") Long passengerId,
                                   @QueryParam("byPassengerAge") Integer passengerAge) {
        return taxiRideService.findTaxiRideBy(startDate, endDate, minCost, maxCost, minDuration, maxDuration, driverId,
                passengerId, passengerAge);
    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    @Path("{taxi-ride_id}/{passenger-id}")
    public TaxiRide deletePassenger(@PathParam("taxi-ride_id") long taxiRideId,
                                    @PathParam("passenger-id") long passengerId) {
        return taxiRideService.deletePassenger(taxiRideId, passengerId);
    }

}
