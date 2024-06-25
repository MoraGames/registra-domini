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

/** Resource "http://localhost:8080/users"
 *  This class is a RESTful resource that allows to manage users. It allows to:
 * 		- Get a list of all users "/"
 * 		- Post a new user "/"
 * 		- Get the information of a user by ID "/{id}"
 * 		- Get the domains registered by a user "/{id}/domains"
**/
@Path("users") /* root path of the resource */
public class UserResource {
	static private List<User> users = new ArrayList<User>();

	/** GET http://localhost:8080/users
	 *  Returns the list of all users
	**/
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsersList() {
        return users;
    }

    /** POST http://localhost:8080/users
     *  Adds a new user
    **/
    @POST
	@Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User user) {
		// Check if the user is not null
		if (user == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		// Check if the user has all the required fields
		if (user.getName() == null || user.getSurname() == null || user.getEmail() == null || user.getPassword() == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		// Check if the user email isn't already registered
		for (User u : users) {
			if (u.getEmail().equals(user.getEmail())) {
				return Response.status(Status.CONFLICT).build();
			}
		}
		
		// Add the user to the list
		users.add(user);

		// If the user can be correctly obtained, the response return the success, otherwise return an error
		try {
			URI uri = new URI("http://localhost:8080/users/" + user.getId().toString());
			return Response.created(uri).build();
		} catch (URISyntaxException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	/** GET http://localhost:8080/users/{id}
     *  Returns the information of a user by ID
    **/
	@Path("/{id}")
    @GET
	@Produces(MediaType.APPLICATION_JSON)
    public Response getUserByID(@PathParam("id") int id) {
		for (User u : users) {
			if (u.getId() == id) {
				return Respone.ok(u).build();
			}
		}
		return Response.status(Status.NOT_FOUND).build();
	}

	/** GET localhost:8080/user/{id}/domains
     *  Returns the domains registered by a user (currently active and expired but not buyed again by someone else)
    **/
	@Path("/{id}/domains")
    @GET
	@Produces(MediaType.APPLICATION_JSON)
    public Response getUserDomainsByID(@PathParam("id") int id) {
		//TODO: Implement this method
		return Response.status(Status.NOT_IMPLEMENTED).build();
	}
}

/*
API (users) Endpoints:
	GET  http://localhost:8080/users/
	POST http://localhost:8080/users/
	GET  http://localhost:8080/users/{id}
	GET  http://localhost:8080/users/{id}/domains
*/