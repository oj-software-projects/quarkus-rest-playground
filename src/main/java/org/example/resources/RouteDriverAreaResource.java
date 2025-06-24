package org.example.resources;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.example.entities.RouteDriverArea;
import org.example.util.QueryOptions;
import org.example.util.QueryUtils;
import io.quarkus.hibernate.orm.panache.Panache;

import java.util.List;

@Path("/rest/route-drive-areas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "RouteDriveAreas")
public class RouteDriverAreaResource {

    @GET
    @Operation(summary = "List all route-drive-area associations")
    public List<RouteDriverArea> getAll(@Context UriInfo uriInfo) {
        QueryOptions options = QueryUtils.from(uriInfo.getQueryParameters(), "id", 10);
        return QueryUtils.find(Panache.getEntityManager(), RouteDriverArea.class, options);
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Retrieve a route-drive-area association by id")
    public RouteDriverArea get(@PathParam("id") Integer id) {
        RouteDriverArea entity = RouteDriverArea.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        return entity;
    }

    @POST
    @Transactional
    @Operation(summary = "Create a new route-drive-area association")
    public Response create(RouteDriverArea rda) {
        rda.persist();
        return Response.status(Response.Status.CREATED).entity(rda).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Delete a route-drive-area association")
    public void delete(@PathParam("id") Integer id) {
        RouteDriverArea entity = RouteDriverArea.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }
}
