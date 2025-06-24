package org.example.resources;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.example.entities.Driver;
import org.example.util.QueryOptions;
import org.example.util.QueryUtils;
import io.quarkus.hibernate.orm.panache.Panache;

import java.util.List;

@Path("/rest/drivers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Drivers")
public class DriverResource {

    @GET
    @Operation(summary = "Retrieve a paginated list of drivers with optional filtering and sorting")
    public List<Driver> getAll(@Context UriInfo uriInfo) {
        QueryOptions options = QueryUtils.from(uriInfo.getQueryParameters(), "id", 10);
        return QueryUtils.find(Panache.getEntityManager(), Driver.class, options);
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Retrieve a driver by id")
    public Driver get(@PathParam("id") Integer id) {
        Driver entity = Driver.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        return entity;
    }

    @POST
    @Transactional
    @Operation(summary = "Create a new driver")
    public Response create(Driver driver) {
        driver.persist();
        return Response.status(Response.Status.CREATED).entity(driver).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Update a driver")
    public Driver update(@PathParam("id") Integer id, Driver driver) {
        Driver entity = Driver.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.firstname = driver.firstname;
        entity.name = driver.name;
        entity.personalNr = driver.personalNr;
        entity.employeeNr = driver.employeeNr;
        entity.birthday = driver.birthday;
        entity.persist();
        return entity;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Delete a driver")
    public void delete(@PathParam("id") Integer id) {
        Driver entity = Driver.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }
}
