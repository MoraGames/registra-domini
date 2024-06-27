package it.unimib.sd2024.models;

import java.util.Date;

public class Contract {
	private User owner;
	private Date acquisitionDate;
	private Date expirationDate;

	public Contract(User owner, Date acquisitionDate, Date expirationDate) {
		this.owner = owner;
		this.acquisitionDate = acquisitionDate;
		this.expirationDate = expirationDate;
	}

	public User getOwner() {
		return this.owner;
	}

	public Date getAcquisitionDate() {
		return this.acquisitionDate;
	}

	public Date getExpirationDate() {
		return this.expirationDate;
	}
}
