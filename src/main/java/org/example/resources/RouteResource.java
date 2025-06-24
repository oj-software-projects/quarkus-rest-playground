package org.example.resources;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.example.entities.Route;
import org.example.util.QueryOptions;
import org.example.util.QueryUtils;
import io.quarkus.hibernate.orm.panache.Panache;

import java.util.List;

@Path("/rest/routes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Routes")
public class RouteResource {

    @GET
    @Operation(summary = "Retrieve list of routes")
    public List<Route> getAll(@Context UriInfo uriInfo) {
        QueryOptions options = QueryUtils.from(uriInfo.getQueryParameters(), "id", 10);
        return QueryUtils.find(Panache.getEntityManager(), Route.class, options);
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Retrieve a route by id")
    public Route get(@PathParam("id") Integer id) {
        Route entity = Route.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        return entity;
    }

    @POST
    @Transactional
    @Operation(summary = "Create a new route")
    public Response create(Route route) {
        route.persist();
        return Response.status(Response.Status.CREATED).entity(route).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Update a route")
    public Route update(@PathParam("id") Integer id, Route route) {
        Route entity = Route.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.routeName = route.routeName;
        entity.routeSortId = route.routeSortId;
        entity.persist();
        return entity;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Delete a route")
    public void delete(@PathParam("id") Integer id) {
        Route entity = Route.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }
}
