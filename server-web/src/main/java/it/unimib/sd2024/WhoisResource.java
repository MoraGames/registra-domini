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
@Path("whois")
public class WhoisResource {
	static private List<Whois> whoisList = new ArrayList<Whois>();

	/** GET http://localhost:8080/whois/{domain}/{user}/{date}
     *  Returns the information of a whois for specified domain, user and date
    **/
	@Path("/{domain}/{user}/{date}")
    @GET
	@Produces(MediaType.APPLICATION_JSON)
    public Integer getUserByID(@PathParam("domain") String domain, @PathParam("user") String user, @PathParam("date") String date) {
		//TODO: Implement this method
	}
}

/*
API (whois) Endpoints:
	GET  http://localhost:8080/whois/{domain}/{user}/{date}
*/