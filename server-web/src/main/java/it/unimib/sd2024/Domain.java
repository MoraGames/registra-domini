package it.unimib.sd2024;

import java.util.Date;

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
	private String value; /* Primary Key */
	private String status;
	private float monthlyCost;
	private Date creationDate;
	private Date lastUpdateDate;

	public Domain(String value, float monthlyCost) {
		this.value = value;
		this.status = "AVAILABLE";
		this.monthlyCost = monthlyCost;
		this.creationDate = new Date();
		this.lastUpdateDate = this.creationDate;
	}

	public String getValue() {
		return this.value;
	}
	
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		if(status != "AVAILABLE" && status != "ACQUIRING" && status != "REGISTERED" && status != "EXPIRED") {
			throw new IllegalArgumentException("Invalid status value (" + status + "). Must be one of [AVAILABLE, ACQUIRING, REGISTERED, EXPIRED].");
		}
		this.status = status;
		this.lastUpdateDate = new Date();
	}

	public float getMonthlyCost() {
		return this.monthlyCost;
	}

	public void setMonthlyCost(float monthlyCost) {
		if (monthlyCost < 0) {
			throw new IllegalArgumentException("Invalid monthly cost value (" + monthlyCost + "). Must be greater than or equal to 0,00");
		}
		this.monthlyCost = monthlyCost;
		this.lastUpdateDate = new Date();
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}
}