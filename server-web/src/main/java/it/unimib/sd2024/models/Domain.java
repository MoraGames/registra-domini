package it.unimib.sd2024.models;

import java.util.Date;
import java.util.regex.Pattern;

/** CLASS Domain
 *  Represents the raw entity structure of a domain resource.
 *  It contains the domain name (primary key) and the current status of it:
 * 		- "AVAILABLE": The domain wasn't previusly registered but now is created and available for purchase (available for purchase)
 *  	- "ACQUIRING": The domain is currently being acquired by someone (not available for purchase)
 *  	- "REGISTERED": The domain is currently registered by someone (not available for purchase)
 *  	- "EXPIRED": The domain was previusly registered but now is available for purchase (available for purchase)
 *  It contains the last acquiring and the last contract to keep track of wich user can be do some operations on the domain.
 *  It also contains the monthly cost of the domain for cost calculation purposes.
**/
public class Domain {
	public static final String DOMAIN_REGEX = "[a-zA-Z0-9][a-zA-Z0-9-]*\\.[a-zA-Z0-9][a-zA-Z0-9-]*"; // A first character only alphanumerical then alphanumerical characters and hyphens, followed by a dot and a first character only alphanumerical then alphanumerical characters and hyphens

	private String name; /* Primary Key */
	private DomainStatus status;
	private Acquiring lastAcquiring;
	private Contract lastContract;
	private float monthlyCost;
	private Date lastUpdateDate;

	public Domain(String name,  float monthlyCost) throws IllegalArgumentException {
		if (!Pattern.matches("^" + DOMAIN_REGEX + "$", name)) {
			throw new IllegalArgumentException("Invalid name value. Must match the DOMAIN_REGEX");
		}
		if (monthlyCost < 0) {
			throw new IllegalArgumentException("Invalid monthly cost value. Must be greater than or equal to 0");
		}
		this.name = name;
		this.status = DomainStatus.AVAILABLE;
		this.lastAcquiring = null;
		this.lastContract = null;
		this.monthlyCost = monthlyCost;
		this.lastUpdateDate = new Date();
	}

	public String getName() {
		return this.name;
	}
	
	public DomainStatus getStatus() {
		return this.status;
	}

	public void setStatus(DomainStatus status) {
		this.status = status;
		this.lastUpdateDate = new Date();
	}

	public Acquiring getLastAcquiring() {
		return this.lastAcquiring;
	}

	public void setLastAcquiring(Acquiring lastAcquiring) {
		this.lastAcquiring = lastAcquiring;
		this.lastUpdateDate = new Date();
	}

	public Contract getLastContract() {
		return this.lastContract;
	}

	public void setLastContract(Contract lastContract) {
		this.lastContract = lastContract;
		this.lastUpdateDate = new Date();
	}

	public float getMonthlyCost() {
		return this.monthlyCost;
	}

	public void setMonthlyCost(float monthlyCost) throws IllegalArgumentException {
		if (monthlyCost < 0) {
			throw new IllegalArgumentException("Invalid monthly cost value. Must be greater than or equal to 0,00");
		}
		this.monthlyCost = monthlyCost;
		this.lastUpdateDate = new Date();
	}

	public Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public DomainInfo info() {
		return new DomainInfo(this.name, this.status);
	}
}