package org.example.resources;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.example.entities.DriverFiles;
import org.example.util.QueryOptions;
import org.example.util.QueryUtils;
import io.quarkus.hibernate.orm.panache.Panache;

import java.util.List;

@Path("/rest/driver-files")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "DriverFiles")
public class DriverFileResource {

    @GET
    @Operation(summary = "Retrieve all driver file entries with optional filtering and sorting")
    public List<DriverFiles> getAll(@Context UriInfo uriInfo) {
        QueryOptions options = QueryUtils.from(uriInfo.getQueryParameters(), "id", 10);
        return QueryUtils.find(Panache.getEntityManager(), DriverFiles.class, options);
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Retrieve a single driver file entry")
    public DriverFiles get(@PathParam("id") Integer id) {
        DriverFiles entity = DriverFiles.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        return entity;
    }

    @POST
    @Transactional
    @Operation(summary = "Create a new driver file entry")
    public Response create(DriverFiles file) {
        file.persist();
        return Response.status(Response.Status.CREATED).entity(file).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Update a driver file entry")
    public DriverFiles update(@PathParam("id") Integer id, DriverFiles file) {
        DriverFiles entity = DriverFiles.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.fileName = file.fileName;
        entity.fileDriver = file.fileDriver;
        entity.fileDate = file.fileDate;
        entity.persist();
        return entity;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Delete a driver file entry")
    public void delete(@PathParam("id") Integer id) {
        DriverFiles entity = DriverFiles.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }
}
