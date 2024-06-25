package it.unimib.sd2024;

/** CLASS Whois
 *  Represents the raw entity structure of a whois resource, a contract associated with an operation carried out by a user for a specific domain.
 *  It contains the domain and the user involved in the operation, the creation and expiration date of the contract and the operation type that can be:
 *  	- "ACQUISITION": The user is acquiring a domain (reserved for future registrations)	
 *  	- "REGISTRATION": The user is registering a new domain
 *  	- "RENEWAL": The user is renewing a domain
 *  It also contains a flag to hide the owner information for privacy purposes.
**/
public class Whois {
	private UUID id; // Primary Key
	private Domain domain;
	private User owner;
	private Date creationDate;
	private String operationType;
	private Date expirationDate;
	private boolean ownerHidden;

	public Whois(Domain domain, User owner, String operationType, int monthDuration, boolean ownerHidden) {
		// Set up basic domain purchase information
		this.id = UUID.randomUUID();
		this.domain = domain;
		this.owner = owner;
		this.creationDate = new Date();
		this.operationType = operationType;
		this.ownerHidden = ownerHidden;

		// Calculate the expirationDate based on the monthDuration
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.creationDate);
		calendar.add(Calendar.MONTH, monthDuration);
		this.expirationDate = calendar.getTime();
	}

	public UUID getId() {
		return this.id;
	}

	public Domain getDomain() {
		return this.domain;
	}

	public User getOwner() {
		if (this.ownerHidden) {
			return null;
		}
		return this.owner;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public String getOperationType() {
		return this.operationType;
	}

	public Date getExpirationDate() {
		return this.expirationDate;
	}

	public boolean isOwnerHidden() {
		return this.ownerHidden;
	}

	public void setOwnerHidden(boolean ownerHidden) {
		this.ownerHidden = ownerHidden;
	}
}