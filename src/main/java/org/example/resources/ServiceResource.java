package org.example.resources;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.example.entities.Service;
import org.example.util.QueryOptions;
import org.example.util.QueryUtils;
import io.quarkus.hibernate.orm.panache.Panache;

import java.util.List;

@Path("/rest/services")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Services")
public class ServiceResource {

    @GET
    @Operation(summary = "Retrieve all services")
    public List<Service> getAll(@Context UriInfo uriInfo) {
        QueryOptions options = QueryUtils.from(uriInfo.getQueryParameters(), "id", 10);
        return QueryUtils.find(Panache.getEntityManager(), Service.class, options);
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Retrieve a service by id")
    public Service get(@PathParam("id") Integer id) {
        Service entity = Service.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        return entity;
    }

    @POST
    @Transactional
    @Operation(summary = "Create a new service")
    public Response create(Service service) {
        service.persist();
        return Response.status(Response.Status.CREATED).entity(service).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Update a service")
    public Service update(@PathParam("id") Integer id, Service service) {
        Service entity = Service.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.serviceName = service.serviceName;
        entity.serviceSortId = service.serviceSortId;
        entity.persist();
        return entity;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Delete a service")
    public void delete(@PathParam("id") Integer id) {
        Service entity = Service.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }
}
