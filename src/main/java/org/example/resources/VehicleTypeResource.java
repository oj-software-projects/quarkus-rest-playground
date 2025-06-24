package org.example.resources;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.example.entities.VehicleTypes;
import org.example.util.QueryOptions;
import org.example.util.QueryUtils;
import io.quarkus.hibernate.orm.panache.Panache;

import java.util.List;

@Path("/rest/vehicles")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Vehicles")
public class VehicleTypeResource {

    @GET
    @Operation(summary = "Retrieve all vehicle types")
    public List<VehicleTypes> getAll(@Context UriInfo uriInfo) {
        QueryOptions options = QueryUtils.from(uriInfo.getQueryParameters(), "id", 10);
        return QueryUtils.find(Panache.getEntityManager(), VehicleTypes.class, options);
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Retrieve a vehicle type by id")
    public VehicleTypes get(@PathParam("id") Integer id) {
        VehicleTypes entity = VehicleTypes.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        return entity;
    }

    @POST
    @Transactional
    @Operation(summary = "Create a new vehicle type")
    public Response create(VehicleTypes vehicle) {
        vehicle.persist();
        return Response.status(Response.Status.CREATED).entity(vehicle).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Update a vehicle type")
    public VehicleTypes update(@PathParam("id") Integer id, VehicleTypes vehicle) {
        VehicleTypes entity = VehicleTypes.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.vehicleName = vehicle.vehicleName;
        entity.vehicleSortId = vehicle.vehicleSortId;
        entity.persist();
        return entity;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Delete a vehicle type")
    public void delete(@PathParam("id") Integer id) {
        VehicleTypes entity = VehicleTypes.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }
}
