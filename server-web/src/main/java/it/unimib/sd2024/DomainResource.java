package it.unimib.sd2024;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import jakarta.json.JsonException;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import java.util.List;
import java.util.ArrayList;

/**
 * 
**/
@Path("domains")
public class DomainResource {
	static private List<Domain> domains = new ArrayList<Domain>();

	/** POST ./domains
     *  Acquires/Register a new domain for a user
    **/
    @POST
	@Consumes(MediaType.APPLICATION_JSON)
    public Response buyDomain() {
		//TODO: Implement this method
		return Response.status(Status.NOT_IMPLEMENTED).build();
	}

	/** GET ./domains/{domain}
     *  Returns the information of a domain by its value
    **/
	@Path("/{domain}")
    @GET
	@Produces(MediaType.APPLICATION_JSON)
    public Response getDomainByValue(@PathParam("domain") String value) {
		//TODO: Implement this method
		return Response.status(Status.NOT_IMPLEMENTED).build();
	}

	/** POST ./domains/{domain}
     *  Extends the registration period of a domain by its value
    **/
	@Path("/{domain}")
    @POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public Response getUserByID(@PathParam("domain") String value) {
		//TODO: Implement this method
		return Response.status(Status.NOT_IMPLEMENTED).build();
	}
}

/*
API (domains) Endpoints:
	GET  http://localhost:8080/domains/
	POST http://localhost:8080/domains/
	GET  http://localhost:8080/domains/{domain}
*/