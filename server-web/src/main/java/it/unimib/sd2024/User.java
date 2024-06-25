package it.unimib.sd2024;

import java.util.UUID;
import java.util.Date;

/** CLASS User
 *  Represents the raw entity structure of a user resource.
 *  It contains the user's information data like name, surname and the email (unique key), password for authentication purposes.
 *  It's identified by a UUID primary key, used in all the request except the sign-in method (done by email property).
**/
public class User {
	private UUID id; // Primary Key
	private String name;
	private String surname;
	private String email; // Unique Key
	private char[] password;
	private Date creationDate;
	private Date lastUpdateDate;

	public User(String name, String surname, String email, char[] password) {
		this.id = UUID.randomUUID();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.creationDate = new Date();
		this.lastUpdateDate = this.creationDate;
	}

	public UUID getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
		this.lastUpdateDate = new Date();
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
		this.lastUpdateDate = new Date();
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
		this.lastUpdateDate = new Date();
	}

	public char[] getPassword() {
		return this.password;
	}

	public void setPassword(char[] password) {
		this.password = password;
		this.lastUpdateDate = new Date();
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}
}