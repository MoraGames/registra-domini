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
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

/**
 * 
**/
@Path("operations")
public class OperationResource {
	static private List<Operation> operationsList = new ArrayList<Operation>();

	/** GET ./operations?domain&user&date
     *  Returns a list of operations with related infos. The list can be filtered by specifing a domain, a user or a date
    **/
    @GET
	@Produces(MediaType.APPLICATION_JSON)
    public Response getUserByID(@QueryParam("domain") String domain, @QueryParam("user") int user, @QueryParam("date") Date date) {
		//TODO: Implement this method
		return Response.status(Status.NOT_IMPLEMENTED).build();
	}
}