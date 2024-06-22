package it.unimib.sd2024;

/**
 * 
**/
public class Whois {
	private Domain domain; /* Primary Key */
	private User owner;
	private Date creationDate;
	private Date lastRenewalDate;
	private Date expirationDate;
	private boolean ownerHidden;

	public Whois(Domain domain, User owner, int monthDuration, boolean ownerHidden) {
		// Set up basic domain purchase information
		this.domain = domain;
		this.owner = owner;
		this.creationDate = new Date();
		this.lastRenewalDate = this.creationDate;
		this.ownerHidden = ownerHidden;

		// Calculate the expirationDate based on the monthDuration
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.creationDate);
		calendar.add(Calendar.MONTH, monthDuration);
		this.expirationDate = calendar.getTime();
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

	public Date getLastRenewalDate() {
		return this.lastRenewalDate;
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

	public void renewFor(int monthDuration) {
		// Calculate the new expirationDate based on the monthDuration
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.expirationDate);
		calendar.add(Calendar.MONTH, monthDuration);
		this.expirationDate = calendar.getTime();

		// Update the lastRenewalDate
		this.lastRenewalDate = new Date();
	}
}