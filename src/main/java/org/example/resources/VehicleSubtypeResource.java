package org.example.resources;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.example.entities.VehicleSubtype;
import org.example.util.QueryOptions;
import org.example.util.QueryUtils;
import io.quarkus.hibernate.orm.panache.Panache;

import java.util.List;

@Path("/rest/vehicle-subtypes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "VehicleSubtypes")
public class VehicleSubtypeResource {

    @GET
    @Operation(summary = "Retrieve all vehicle subtypes")
    public List<VehicleSubtype> getAll(@Context UriInfo uriInfo) {
        QueryOptions options = QueryUtils.from(uriInfo.getQueryParameters(), "id", 10);
        return QueryUtils.find(Panache.getEntityManager(), VehicleSubtype.class, options);
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Retrieve a vehicle subtype by id")
    public VehicleSubtype get(@PathParam("id") Integer id) {
        VehicleSubtype entity = VehicleSubtype.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        return entity;
    }

    @POST
    @Transactional
    @Operation(summary = "Create a new vehicle subtype")
    public Response create(VehicleSubtype subtype) {
        subtype.persist();
        return Response.status(Response.Status.CREATED).entity(subtype).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Update a vehicle subtype")
    public VehicleSubtype update(@PathParam("id") Integer id, VehicleSubtype subtype) {
        VehicleSubtype entity = VehicleSubtype.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.vehicleSubtypeName = subtype.vehicleSubtypeName;
        entity.persist();
        return entity;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Delete a vehicle subtype")
    public void delete(@PathParam("id") Integer id) {
        VehicleSubtype entity = VehicleSubtype.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }
}
