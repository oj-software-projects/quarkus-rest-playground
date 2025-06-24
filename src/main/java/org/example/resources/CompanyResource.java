package org.example.resources;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.example.entities.Company;
import org.example.util.QueryOptions;
import org.example.util.QueryUtils;
import io.quarkus.hibernate.orm.panache.Panache;

import java.util.List;

@Path("/rest/companies")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Companies")
public class CompanyResource {

    @GET
    @Operation(summary = "Retrieve a paginated list of companies with optional filtering and sorting")
    public List<Company> getAll(@Context UriInfo uriInfo) {
        QueryOptions options = QueryUtils.from(uriInfo.getQueryParameters(), "id", 10);
        return QueryUtils.find(Panache.getEntityManager(), Company.class, options);
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Retrieve a company by id")
    public Company get(@PathParam("id") Integer id) {
        Company entity = Company.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        return entity;
    }

    @POST
    @Transactional
    @Operation(summary = "Create a new company")
    public Response create(Company company) {
        company.persist();
        return Response.status(Response.Status.CREATED).entity(company).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Update an existing company")
    public Company update(@PathParam("id") Integer id, Company company) {
        Company entity = Company.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.companyName = company.companyName;
        entity.companySortId = company.companySortId;
        entity.persist();
        return entity;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Delete a company by id")
    public void delete(@PathParam("id") Integer id) {
        Company entity = Company.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }
}
