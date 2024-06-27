package it.unimib.sd2024.models;

import java.util.Date;

/** CLASS Whois
 *  Represents the raw entity structure of a whois resource, a contract associated with an operation carried out by a user for a specific domain.
 *  It contains the domain and the user involved in the operation, the creation and expiration date of the contract and the operation type that can be:
 *  	- "ACQUISITION": The user is acquiring a domain (reserved for future registrations)	
 *  	- "REGISTRATION": The user is registering a new domain
 *  	- "RENEWAL": The user is renewing a domain
 *  It also contains a flag to hide the owner information for privacy purposes.
**/
public class Operation {
	private User owner;
	private Domain domain;
	private OperationType type;
	private float cost;
	private Date date;

	public Operation(User owner, Domain domain, OperationType type, int monthDuration) {
		// Set up basic domain purchase information
		this.owner = owner;
		this.domain = domain;
		this.type = type;
		this.cost = domain.getMonthlyCost()*monthDuration;
		this.date = new Date();
	}
	
	public User getOwner() {
		return this.owner;
	}
	
	public Domain getDomain() {
		return this.domain;
	}
	
	public Date getDate() {
		return this.date;
	}

	public OperationType getType() {
		return this.type;
	}

	public float getCost() {
		return this.cost;
	}

	public OperationInfo info() {
		return new OperationInfo(this.owner, this.domain, this.type, this.cost);
	}
}