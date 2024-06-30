package it.unimib.sd2024.connection;

import java.util.ArrayList;
import java.util.List;

import it.unimib.sd2024.models.User;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import it.unimib.sd2024.models.Domain;
import it.unimib.sd2024.models.Operation;
import it.unimib.sd2024.models.OperationType;

public class Queryer {
	/** queryFindDomainByName()
	 *  Queries the database for a find operation on domains with the same name as the one passed as a parameter.
	 *  Returns the domain found or null if not found.
	**/
	public static final Domain queryFindDomainByName(String domainName) {
		String response = "";
		try {
			response = DatabaseConnector.Communicate("SELECT domains\nSEARCH name = \"" + domainName + "\"");
		} catch (Exception e) {
			System.err.println("[ERROR] Error while communicating with the database: " + e.getMessage());
			return null;
		}
		if (response.startsWith("[SUCCESS]")) {
			Jsonb jsonb = JsonbBuilder.create();
			Domain domain = jsonb.fromJson(response.substring(9), Domain.class); // Assuming the response format is "[SUCCESS] followed by JSON"
			return domain;
		} else {
			System.err.println("[ERROR] Database response: " + response);
			return null;
		}
	}

	/** queryInsertDomain()
	 *  Queries the database for an insert operation on the domain passed as a parameter.
	 *  Returns the domain inserted or null if not inserted.
	**/
	public static final Domain queryInsertDomain(Domain domain) {
		String response = "";
		Jsonb jsonb = JsonbBuilder.create();
		String json = jsonb.toJson(domain);

		try {
			response = DatabaseConnector.Communicate("SELECT domains\nINSERT " + json);
		} catch (Exception e) {
			System.err.println("[ERROR] Error while communicating with the database: " + e.getMessage());
			return null;
		}

		if (response.startsWith("[SUCCESS]")) {
			return domain;
		} else {
			System.err.println("[ERROR] Database response: " + response);
			return null;
		}
	}

	/** queryUpdateDomain()
	 *  Queries the database for an update operation on the domain passed as a parameter.
	 *  Returns the domain updated or null if not updated.
	**/
	public static final Domain queryUpdateDomain(Domain domain) {
		String response = "";
		Jsonb jsonb = JsonbBuilder.create();
		String json = jsonb.toJson(domain);

		try {
			response = DatabaseConnector.Communicate("SELECT domains\nREMOVE " + domain.getName() + "\nINSERT " + json);
		} catch (Exception e) {
			System.err.println("[ERROR] Error while communicating with the database: " + e.getMessage());
			return null;
		}

		if (response.startsWith("[SUCCESS]")) {
			return domain;
		} else {
			System.err.println("[ERROR] Database response: " + response);
			return null;
		}
	}

	/** queryFindUserByEmail()
	 *  Queries the database for a find operation on users with the same email as the one passed as a parameter.
	 *  Returns the user found or null if not found.
	**/
	public static final User queryFindUserByEmail(String email) {
		String response = "";
		try {
			response = DatabaseConnector.Communicate("SELECT users\nSEARCH email = \"" + email + "\"");
		} catch (Exception e) {
			System.err.println("[ERROR] Error while communicating with the database: " + e.getMessage());
			return null;
		}

		if (response.startsWith("[SUCCESS]")) {
			Jsonb jsonb = JsonbBuilder.create();
			User user = jsonb.fromJson(response.substring(9), User.class); // Assuming the response format is "[SUCCESS] followed by JSON"
			return user;
		} else {
			System.err.println("[ERROR] Database response: " + response);
			return null;
		}
	}

	/** queryFindUserById()
	 *  Queries the database for a find operation on users with the same id as the one passed as a parameter.
	 *  Returns the user found or null if not found.
	**/
	public static final User queryFindUserById(Long userId) {
		String response = "";
		try {
			response = DatabaseConnector.Communicate("SELECT users\nSEARCH id = \"" + userId + "\"");
		} catch (Exception e) {
			System.err.println("[ERROR] Error while communicating with the database: " + e.getMessage());
			return null;
		}

		if (response.startsWith("[SUCCESS]")) {
			Jsonb jsonb = JsonbBuilder.create();
			User user = jsonb.fromJson(response.substring(9), User.class); // Assuming the response format is "[SUCCESS] followed by JSON"
			return user;
		} else {
			System.err.println("[ERROR] Database response: " + response);
			return null;
		}
	}

	/** queryInsertUser()
	 *  Queries the database for an insert operation on the user passed as a parameter.
	 *  Returns the user inserted or null if not inserted.
	**/
	public static final User queryInsertUser(User user) {
		// Retrieve the response from the database for the prepared query sent
		String response = "";
		try {
			response = DatabaseConnector.Communicate("SELECT \"users\"\nINSERT "+ JsonbBuilder.create().toJson(user));
		} catch (Exception e) {
			System.err.println("[ERROR] Error while communicating with the database: " + e.getMessage());
		}
		
		// Check the response from the database
		String[] splittedResponse = response.split(" ", 1);
		switch(splittedResponse[0]) {
			case "[SUCCESS]":
				return user;
			case "[ERROR]":
				return null;
			case "[FAIL]":
				return null;
			default:
				System.err.println("[ERROR] Database response unknown: " + response);
				return null;
		}
	}

	/** queryFindOperations()
	 *  Queries the database for a find operation on operations with the same user and domain as the ones passed as parameters.
	 *  Returns the list of operations found or an empty list if not found.
	**/
	public static final List<Operation> queryFindOperations(User userFilter, Domain domainFilter, OperationType operationTypeFilter) {
		String response = "";
		try {
			String query = "SELECT operations\nSEARCH ";
			if (userFilter != null) {
				query += "user = \"" + userFilter.getId() + "\" ";
			}
			if (domainFilter != null) {
				query += "AND domain = \"" + domainFilter.getName() + "\" ";
			}
			if (operationTypeFilter != null) {
				query += "AND operationType = \"" + operationTypeFilter.name() + "\"";
			}

			response = DatabaseConnector.Communicate(query.trim());
		} catch (Exception e) {
			System.err.println("[ERROR] Error while communicating with the database: " + e.getMessage());
			return List.of();
		}

		if (response.startsWith("[SUCCESS]")) {
			Jsonb jsonb = JsonbBuilder.create();
			List<Operation> operations = jsonb.fromJson(response.substring(9), new ArrayList<Operation>(){}.getClass().getGenericSuperclass());
			return operations;
		} else {
			System.err.println("[ERROR] Database response: " + response);
			return List.of();
		}
	}

	/** queryInsertOperation()
	 *  Queries the database for an insert operation on the operation passed as a parameter.
	 *  Returns the operation inserted or null if not inserted.
	**/
	public static final Operation queryInsertOperation(Operation operation) {
		String response = "";
		Jsonb jsonb = JsonbBuilder.create();
		String json = jsonb.toJson(operation);

		try {
			response = DatabaseConnector.Communicate("SELECT operations\nINSERT " + json);
		} catch (Exception e) {
			System.err.println("[ERROR] Error while communicating with the database: " + e.getMessage());
			return null;
		}

		if (response.startsWith("[SUCCESS]")) {
			return operation;
		} else {
			System.err.println("[ERROR] Database response: " + response);
			return null;
		}
	}
}
