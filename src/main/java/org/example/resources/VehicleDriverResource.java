package org.example.resources;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.example.entities.VehicleDriver;
import org.example.util.QueryOptions;
import org.example.util.QueryUtils;
import io.quarkus.hibernate.orm.panache.Panache;

import java.util.List;

@Path("/rest/driver-vehicles")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "DriverVehicles")
public class VehicleDriverResource {

    @GET
    @Operation(summary = "Retrieve all driver-vehicle associations")
    public List<VehicleDriver> getAll(@Context UriInfo uriInfo) {
        QueryOptions options = QueryUtils.from(uriInfo.getQueryParameters(), "id", 10);
        return QueryUtils.find(Panache.getEntityManager(), VehicleDriver.class, options);
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Retrieve a driver-vehicle association by id")
    public VehicleDriver get(@PathParam("id") Integer id) {
        VehicleDriver entity = VehicleDriver.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        return entity;
    }

    @POST
    @Transactional
    @Operation(summary = "Create a new driver-vehicle association")
    public Response create(VehicleDriver vd) {
        vd.persist();
        return Response.status(Response.Status.CREATED).entity(vd).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Delete a driver-vehicle association")
    public void delete(@PathParam("id") Integer id) {
        VehicleDriver entity = VehicleDriver.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }
}
