package org.example.resources;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.example.entities.RouteDriver;
import org.example.util.QueryOptions;
import org.example.util.QueryUtils;
import io.quarkus.hibernate.orm.panache.Panache;

import java.util.List;

@Path("/rest/driver-routes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "DriverRoutes")
public class RouteDriverResource {

    @GET
    @Operation(summary = "Retrieve all driver-route associations")
    public List<RouteDriver> getAll(@Context UriInfo uriInfo) {
        QueryOptions options = QueryUtils.from(uriInfo.getQueryParameters(), "id", 10);
        return QueryUtils.find(Panache.getEntityManager(), RouteDriver.class, options);
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Retrieve a driver-route association by id")
    public RouteDriver get(@PathParam("id") Integer id) {
        RouteDriver entity = RouteDriver.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        return entity;
    }

    @POST
    @Transactional
    @Operation(summary = "Create a new driver-route association")
    public Response create(RouteDriver rd) {
        rd.persist();
        return Response.status(Response.Status.CREATED).entity(rd).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Delete a driver-route association")
    public void delete(@PathParam("id") Integer id) {
        RouteDriver entity = RouteDriver.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }
}
