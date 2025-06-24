package org.example.resources;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.example.entities.Depot;
import org.example.util.QueryOptions;
import org.example.util.QueryUtils;
import io.quarkus.hibernate.orm.panache.Panache;

import java.util.List;

@Path("/rest/depots")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Depots")
public class DepotResource {

    @GET
    @Operation(summary = "Retrieve all depots")
    public List<Depot> getAll(@Context UriInfo uriInfo) {
        QueryOptions options = QueryUtils.from(uriInfo.getQueryParameters(), "id", 10);
        return QueryUtils.find(Panache.getEntityManager(), Depot.class, options);
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Retrieve a depot by id")
    public Depot get(@PathParam("id") Integer id) {
        Depot entity = Depot.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        return entity;
    }

    @POST
    @Transactional
    @Operation(summary = "Create a new depot")
    public Response create(Depot depot) {
        depot.persist();
        return Response.status(Response.Status.CREATED).entity(depot).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Update a depot")
    public Depot update(@PathParam("id") Integer id, Depot depot) {
        Depot entity = Depot.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.depotName = depot.depotName;
        entity.depotSortId = depot.depotSortId;
        entity.location = depot.location;
        entity.persist();
        return entity;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Delete a depot")
    public void delete(@PathParam("id") Integer id) {
        Depot entity = Depot.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }
}
