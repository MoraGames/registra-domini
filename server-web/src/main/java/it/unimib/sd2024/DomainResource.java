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

/**
 * 
**/
@Path("domains")
public class DomainResource {
	static private List<Domain> domains = new ArrayList<Domain>();

	/** GET http://localhost:8080/domains
	 *  Returns the list of all domains currently registered
	**/
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Domain> getDomainsList() {
        List<Domain> registeredDomains = new ArrayList<Domain>();
		for (Domain d : domains) {
			if (d.getStatus().equals("REGISTERED")) {
				registeredDomains.add(d);
			}
		}
		return registeredDomains;
    }

    /** POST http://localhost:8080/domains
     *  Buy a new domain
    **/
    @POST
	@Consumes(MediaType.APPLICATION_JSON)
    public Response buyDomain(Domain domain, User user) {
		//TODO: Implement this method
	}

	/** GET http://localhost:8080/domains/{domain}
     *  Returns the information of a domain by its value
    **/
	@Path("/{domain}")
    @GET
	@Produces(MediaType.APPLICATION_JSON)
    public Integer getUserByID(@PathParam("domain") int id) {
		//TODO: Implement this method
	}
}

/*
API (domains) Endpoints:
	GET  http://localhost:8080/domains/
	POST http://localhost:8080/domains/
	GET  http://localhost:8080/domains/{domain}
*/