package it.unimib.sd2024.comunicator;

import java.util.List;

import it.unimib.sd2024.models.User;
import it.unimib.sd2024.models.Domain;
import it.unimib.sd2024.models.Operation;
import it.unimib.sd2024.models.OperationType;

public class Queryer {
	/** queryFindDomainByName()
	 *  Queries the database for a find operation on domains with the same name as the one passed as a parameter.
	 *  Returns the domain found or null if not found.
	**/
	public static final Domain queryFindDomainByName(String domainName) {
		// TODO: Implement this method
			// queries the database for a find operation on domains with the same name as the one passed as a parameter.
		return null;
	}

	/** queryInsertDomain()
	 *  Queries the database for an insert operation on the domain passed as a parameter.
	 *  Returns the domain inserted or null if not inserted.
	**/
	public static final Domain queryInsertDomain(Domain domain) {
		// TODO: Implement this method
			// queries the database for an insert operation on the domain passed as a parameter.
		return null;
	}

	/** queryUpdateDomain()
	 *  Queries the database for an update operation on the domain passed as a parameter.
	 *  Returns the domain updated or null if not updated.
	**/
	public static final Domain queryUpdateDomain(Domain domain) {
		// TODO: Implement this method
			// queries the database for an update operation on the domain passed as a parameter.
		return null;
	}

	/** queryFindUserByEmail()
	 *  Queries the database for a find operation on users with the same email as the one passed as a parameter.
	 *  Returns the user found or null if not found.
	**/
	public static final User queryFindUserByEmail(String email) {
		// TODO: Implement this method
			// queries the database for a find operation on users with the same email as the one passed as a parameter.
		return null;
	}

	/** queryFindUserById()
	 *  Queries the database for a find operation on users with the same id as the one passed as a parameter.
	 *  Returns the user found or null if not found.
	**/
	public static final User queryFindUserById(Long userId) {
		// TODO: Implement this method
			// queries the database for a find operation on users with the same id as the one passed as a parameter.
		return null;
	}

	/** queryInsertUser()
	 *  Queries the database for an insert operation on the user passed as a parameter.
	 *  Returns the user inserted or null if not inserted.
	**/
	public static final User queryInsertUser(User user) {
		// TODO: Implement this method
			// queries the database for an insert operation on the user passed as a parameter.
		return null;
	}

	/** queryFindOperations()
	 *  Queries the database for a find operation on operations with the same user and domain as the ones passed as parameters.
	 *  Returns the list of operations found or an empty list if not found.
	**/
	public static final List<Operation> queryFindOperations(User userFilter, Domain domainFilter, OperationType operationTypeFilter) {
		// TODO: Implement this method
			// queries the database for a find operation on operations applying the filters (user, domain, operationType) passed as parameters.
		return null;
	}

	/** queryInsertOperation()
	 *  Queries the database for an insert operation on the operation passed as a parameter.
	 *  Returns the operation inserted or null if not inserted.
	**/
	public static final Operation queryInsertOperation(Operation operation) {
		// TODO: Implement this method
			// queries the database for an insert operation on the operation passed as a parameter.
		return null;
	}
}
