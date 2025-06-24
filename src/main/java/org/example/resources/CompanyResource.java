package org.example.resources;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.example.entities.Company;

import java.util.List;

@Path("/rest/companies")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Companies")
public class CompanyResource {

    @GET
    @Operation(summary = "Retrieve a paginated list of companies")
    public List<Company> getAll(@QueryParam("skip") @DefaultValue("0") int skip,
                               @QueryParam("take") @DefaultValue("10") int take) {
        return Company.findAll().range(skip, skip + take - 1).list();
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
