package it.unimib.sd2024.resources;

import java.util.Date;
import java.util.Random;
import java.util.Calendar;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import it.unimib.sd2024.request.NewDomainRequestBody;
import it.unimib.sd2024.request.RenewDomainByNameRequestBody;
import it.unimib.sd2024.models.User;
import it.unimib.sd2024.models.Domain;
import it.unimib.sd2024.models.Contract;
import it.unimib.sd2024.connection.Queryer;
import it.unimib.sd2024.models.Acquiring;
import it.unimib.sd2024.models.Operation;
import it.unimib.sd2024.models.DomainStatus;
import it.unimib.sd2024.models.OperationType;
import it.unimib.sd2024.models.DomainRequestAction;

/**
 * 
**/
@Path("domains")
public class DomainResource {
	private static final float RANDOM_PRICE_MIN = 0.10f;
	private static final float RANDOM_PRICE_MAX = 25.00f;

	/** POST ./domains
	 *  Acquires/Register a new domain for a user
	**/
	@Path("/new")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response newDomain(NewDomainRequestBody body) {
		// Check if the body is not null and has all the required fields valid
		if (body == null || body.getRequestAction() == null || body.getDomainName() == null || body.getUserId() == null) {
			return Response.status(Status.BAD_REQUEST.getStatusCode(), "newDomain :: body must be indicated and with all fields valid").build();
		}

		// Get the domain finding it on the db by name
		Domain d = Queryer.queryFindDomainByName(body.getDomainName())[0];
		
		// Determine whether the user is acquiring a domain or has finished acquiring a domain
		switch (body.getRequestAction()) {
			case DomainRequestAction.ACQUIRING:
				if (d == null) {
					// If the domain is not found, create a new domain with a random price
					d = new Domain(body.getDomainName(), RANDOM_PRICE_MIN + new Random().nextFloat() * (RANDOM_PRICE_MAX - RANDOM_PRICE_MIN));
				} else {
					// If the domain is found, check if the domain can be acquired (it must be currently EXPIRED)
					if (d.getStatus() != DomainStatus.EXPIRED) {
						return Response.status(Status.CONFLICT.getStatusCode(), "newDomain :: domain can not be acquired").build();
					}
				}

				// Update the domain status to ACQUIRING and set the last acquiring
				d.setStatus(DomainStatus.ACQUIRING);
				d.setLastAcquiring(new Acquiring(Queryer.queryFindUserById(body.getUserId())[0], new Date()));

				// Update the domain on the db
				Queryer.queryUpdateDomain(d);
				break;
			case DomainRequestAction.ACQUIRED:
				if (d == null || d.getStatus() != DomainStatus.ACQUIRING) {
					return Response.status(Status.CONFLICT.getStatusCode(), "newDomain :: domain can not be acquired").build();
				}
				
				// Check if the duration of the acquisition is at least 12 months (1 year) and at most 120 months (10 years)
				if(body.getMonthsDuration() < 12 || body.getMonthsDuration() > 120) {
					return Response.status(Status.BAD_REQUEST.getStatusCode(), "newDomain :: monthDuration must be between 12 and 120").build();
				}
				
				// Check if the acquisition was started by the user
				User u = Queryer.queryFindUserById(body.getUserId())[0];
				if (u == null || d.getLastAcquiring().getUser().getId() != u.getId()) {
					return Response.status(Status.FORBIDDEN.getStatusCode(), "newDomain :: user is not authorized to complete the domain acquisition").build();
				}

				// Update the domain status to REGISTERED
				d.setStatus(DomainStatus.REGISTERED);
				
				// Update the FinishAquisitionDate of the last acquiring
				d.getLastAcquiring().setFinishAquisitionDate(new Date());

				// Create the new contract and set it as the last contract of the domain
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(d.getLastAcquiring().getFinishAquisitionDate());
				calendar.add(Calendar.MONTH, body.getMonthsDuration());
				d.setLastContract(new Contract(u, d.getLastAcquiring().getFinishAquisitionDate(), calendar.getTime()));

				// Create the related operation
				Operation operation = new Operation(u, d, OperationType.REGISTRATION, 0);

				// Update the data on the db
				Queryer.queryUpdateDomain(d);
				Queryer.queryInsertOperation(operation);
				break;
			default:
				return Response.status(Status.BAD_REQUEST.getStatusCode(), "newDomain :: status must be either ACQUIRING or ACQUIRED").build();
		}

		// Return the updated domain
		return Response.ok(d.info()).build();
	}

	/** GET ./domains/{domain}
	 *  Returns the information of a domain by its name
	**/
	@Path("/{domainName}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDomainByName(@PathParam("domainName") String domainName) {
		// Get the domain finding it on the db by name
		Domain d = Queryer.queryFindDomainByName(domainName)[0];
		
		// Check if the domain is correctly obtained
		if (d == null || d.getName() != domainName) {
			return Response.status(Status.NOT_FOUND.getStatusCode(), "getUserById :: user not found").build();
		}
		return Response.ok(d.info()).build();
	}

	/** POST ./domains/{domain}/renew
	 *  Extends the registration period of a domain by its name
	**/
	@Path("/{domainName}/renew")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response renewDomainByName(@PathParam("domainName") String domainName, RenewDomainByNameRequestBody body) {
		// Get the domain finding it on the db by name
		Domain d = Queryer.queryFindDomainByName(domainName)[0];

		// Check if the domain is correctly obtained
		if (d == null || d.getName() != domainName) {
			return Response.status(Status.NOT_FOUND.getStatusCode(), "renewDomainByName :: domain not found").build();
		}

		// Check if the domain can be renewed (it must be currently REGISTERED)
		if (d.getStatus() != DomainStatus.REGISTERED) {
			return Response.status(Status.FORBIDDEN.getStatusCode(), "renewDomainByName :: domain is not registered").build();
		}

		// Get the user finding it on the db by id
		User u = Queryer.queryFindUserById(body.getUserId())[0];
		if (u == null) {
			return Response.status(Status.NOT_FOUND.getStatusCode(), "renewDomainByName :: user not found").build();
		}

		// Check if the domain is owned by the user (last contract owner must be the user)
		Contract lastContract = d.getLastContract();
		if (lastContract.getOwner().getId() != u.getId()) {
			return Response.status(Status.FORBIDDEN.getStatusCode(), "renewDomainByName :: user is not the owner of the domain").build();
		}

		// Calculate the new acquisition and expiration dates
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(lastContract.getExpirationDate());
		calendar.add(Calendar.DATE, 1);
		Date newAcquisitionDate = calendar.getTime();
		calendar.add(Calendar.MONTH, body.getMonthsDuration());
		Date newExpirationDate = calendar.getTime();

		// Create the new contract and set it as the last contract of the domain
		d.setLastContract(new Contract(u, newAcquisitionDate, newExpirationDate));

		// Create the related operation
		Operation operation = new Operation(u, d, OperationType.RENEWAL, body.getMonthsDuration());

		// Update the data on the db
		Queryer.queryUpdateDomain(d);
		Queryer.queryInsertOperation(operation);

		// Return the updated domain
		return Response.ok(d.info()).build();
	}
}