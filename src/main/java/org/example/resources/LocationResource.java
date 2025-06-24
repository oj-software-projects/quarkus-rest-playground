package org.example.resources;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.example.entities.Location;
import org.example.util.QueryOptions;
import org.example.util.QueryUtils;
import io.quarkus.hibernate.orm.panache.Panache;

import java.util.List;

@Path("/rest/locations")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Locations")
public class LocationResource {

    @GET
    @Operation(summary = "Retrieve list of locations")
    public List<Location> getAll(@Context UriInfo uriInfo) {
        QueryOptions options = QueryUtils.from(uriInfo.getQueryParameters(), "id", 10);
        return QueryUtils.find(Panache.getEntityManager(), Location.class, options);
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Retrieve a location by id")
    public Location get(@PathParam("id") Integer id) {
        Location entity = Location.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        return entity;
    }

    @POST
    @Transactional
    @Operation(summary = "Create a new location")
    public Response create(Location location) {
        location.persist();
        return Response.status(Response.Status.CREATED).entity(location).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Update a location")
    public Location update(@PathParam("id") Integer id, Location location) {
        Location entity = Location.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.locationName = location.locationName;
        entity.locationToken = location.locationToken;
        entity.persist();
        return entity;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Delete a location")
    public void delete(@PathParam("id") Integer id) {
        Location entity = Location.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }
}
