package it.unimib.sd2024.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import it.unimib.sd2024.models.User;
import it.unimib.sd2024.connection.Queryer;
import it.unimib.sd2024.models.Domain;
import it.unimib.sd2024.models.Operation;
import it.unimib.sd2024.models.OperationType;

/**
 * 
**/
@Path("operations")
public class OperationResource {
	/** GET ./operations[?<userId="userId">[&domainName="domainName"][&operationType="operationType"]]
	 *  Returns a list of operations with related infos. The list can be filtered by specifying one or more parameters among user, domain and type.
	**/
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOperationsList(@QueryParam("userId") Long userId, @QueryParam("domainName") String domainName, @QueryParam("operationType") OperationType operationType) {
		// If specified, get the user by id, otherwise set it to null
		User user = null;
		if (userId != null) {
			// Get the user finding it on the db by id
			user = Queryer.queryFindUserById(userId)[0];
			if (user == null) {
				return Response.status(Status.NOT_FOUND.getStatusCode(), "getOperationsList :: user not found").build();
			}
		}

		// If specified, get the domain by name, otherwise set it to null
		Domain domain = null;
		if (domainName != null) {
			// Get the domain finding it on the db by name
			domain = Queryer.queryFindDomainByName(domainName)[0];
			if (domain == null) {
				return Response.status(Status.NOT_FOUND.getStatusCode(), "getOperationsList :: domain not found").build();
			}
		}

		// Get the list of operations applying the filters		
		Operation[] operations = Queryer.queryFindOperations(user, domain, operationType);
		return Response.ok(operations).build();
	}
}