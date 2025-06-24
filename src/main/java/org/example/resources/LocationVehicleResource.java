package org.example.resources;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.example.entities.LocationVehicle;
import org.example.util.QueryOptions;
import org.example.util.QueryUtils;
import io.quarkus.hibernate.orm.panache.Panache;

import java.util.List;

@Path("/rest/location-vehicles")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "LocationVehicles")
public class LocationVehicleResource {

    @GET
    @Operation(summary = "Retrieve all location-vehicle associations")
    public List<LocationVehicle> getAll(@Context UriInfo uriInfo) {
        QueryOptions options = QueryUtils.from(uriInfo.getQueryParameters(), "id", 10);
        return QueryUtils.find(Panache.getEntityManager(), LocationVehicle.class, options);
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Retrieve a location-vehicle association by id")
    public LocationVehicle get(@PathParam("id") Integer id) {
        LocationVehicle entity = LocationVehicle.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        return entity;
    }

    @POST
    @Transactional
    @Operation(summary = "Create a new location-vehicle association")
    public Response create(LocationVehicle lv) {
        lv.persist();
        return Response.status(Response.Status.CREATED).entity(lv).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Delete a location-vehicle association")
    public void delete(@PathParam("id") Integer id) {
        LocationVehicle entity = LocationVehicle.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }
}
