package it.unimib.sd2024;

/**
 * 
**/
public class Domain {
	private String value; /* Primary Key */
	private String status;
	private float monthlyCost;

	public Domain(String value, float monthlyCost) {
		this.value = value;
		this.monthlyCost = monthlyCost;
	}

	public String getValue() {
		return this.value;
	}

	public float getMonthlyCost() {
		return this.monthlyCost;
	}

	public void setMonthlyCost(float monthlyCost) {
		this.monthlyCost = monthlyCost;
	}
}

// Domain.status:
// - "" (no object available): The domain was never registered (it's available for purchase)
// - "AVAILABLE": The domain was previusly registered but now is available for purchase
// - "REGISTERED": The domain is currently registered by someone
// - "TO_EXPIRE": The domain is currently registered by someone but it's about to expire