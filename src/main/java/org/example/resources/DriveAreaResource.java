package org.example.resources;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.example.entities.DriveArea;
import org.example.util.QueryOptions;
import org.example.util.QueryUtils;
import io.quarkus.hibernate.orm.panache.Panache;

import java.util.List;

@Path("/rest/drive-areas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "DriveAreas")
public class DriveAreaResource {

    @GET
    @Operation(summary = "Retrieve all drive areas")
    public List<DriveArea> getAll(@Context UriInfo uriInfo) {
        QueryOptions options = QueryUtils.from(uriInfo.getQueryParameters(), "id", 10);
        return QueryUtils.find(Panache.getEntityManager(), DriveArea.class, options);
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Retrieve a drive area by id")
    public DriveArea get(@PathParam("id") Integer id) {
        DriveArea entity = DriveArea.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        return entity;
    }

    @POST
    @Transactional
    @Operation(summary = "Create a new drive area")
    public Response create(DriveArea area) {
        area.persist();
        return Response.status(Response.Status.CREATED).entity(area).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Update a drive area")
    public DriveArea update(@PathParam("id") Integer id, DriveArea area) {
        DriveArea entity = DriveArea.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.driveareaName = area.driveareaName;
        entity.persist();
        return entity;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Delete a drive area")
    public void delete(@PathParam("id") Integer id) {
        DriveArea entity = DriveArea.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }
}
