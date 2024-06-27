package it.unimib.sd2024;

import java.util.Date;
import java.util.regex.Pattern;

/** CLASS Domain
 *  Represents the raw entity structure of a domain resource.
 *  It contains the domain name (primary key) and the current status of it:
 *  	- "AVAILABLE": The domain is currently available for purchase (available for purchase)
 *  	- "ACQUIRING": The domain is currently being acquired by someone (not available for purchase)
 *  	- "REGISTERED": The domain is currently registered by someone (not available for purchase)
 *  	- "EXPIRED": The domain was previusly registered but now is available for purchase (available for purchase)
 *  It also contains the monthly cost of the domain for cost calculation purposes.
**/
public class Domain {
	public static final String DOMAIN_REGEX = "[a-zA-Z0-9][a-zA-Z0-9-]*\\.[a-zA-Z0-9][a-zA-Z0-9-]*";

	private String name; /* Primary Key */
	private String status;
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
		this.status = "AVAILABLE";
		this.monthlyCost = monthlyCost;
		this.lastUpdateDate = new Date();
	}

	public String getName() {
		return this.name;
	}
	
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) throws IllegalArgumentException {
		if(status != "AVAILABLE" && status != "ACQUIRING" && status != "REGISTERED" && status != "EXPIRED") {
			throw new IllegalArgumentException("Invalid status value. Must be one of [AVAILABLE, ACQUIRING, REGISTERED, EXPIRED].");
		}
		this.status = status;
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