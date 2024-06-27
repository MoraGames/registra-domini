package it.unimib.sd2024;

import java.util.regex.Pattern;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/** CLASS User
 *  Represents the raw entity structure of a user resource.
 *  It contains the user's information data like name, surname and the email (unique key), password for authentication purposes.
 *  It's identified by a UUID primary key, used in all the request except the sign-in method (done by email property).
**/
public class User {
	public static final String EMAIL_REGEX = "[a-zA-Z0-9-\\.]+@" + Domain.DOMAIN_REGEX;
	public static final String PASSWORD_REGEX = "(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-_]).{8,}";
	private static long lastId = -1;
	
	private long id; // Primary Key
	private String name;
	private String surname;
	private String email; // Unique Key
	private String password;
	private Date creationDate;
	private Date lastUpdateDate;

	public User(String name, String surname, String email, String password) throws IllegalArgumentException, InstantiationException {
		if (!Pattern.matches("^" + EMAIL_REGEX + "$", email)) {
			throw new IllegalArgumentException("Invalid email value. Must match the PASSWORD_REGEX");
		}
		if (!Pattern.matches("^" + PASSWORD_REGEX + "$", password)) {
			throw new IllegalArgumentException("Invalid password value. Must match the PASSWORD_REGEX");
		}
		try {
			MessageDigest mdi = MessageDigest.getInstance("SHA-256");
			this.id = ++lastId;
			this.name = name;
			this.surname = surname;
			this.email = email;
			this.password = new String(mdi.digest(password.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
			this.creationDate = new Date();
			this.lastUpdateDate = this.creationDate;
		} catch (NoSuchAlgorithmException e) {
			throw new InstantiationException("Invalid hash algorithm. Must exist for the MessageDigest class");
		}
	}

	public long getId() {
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

	public void setEmail(String email) throws IllegalArgumentException {
		if (!Pattern.matches("^" + EMAIL_REGEX + "$", email)) {
			throw new IllegalArgumentException("Invalid email value. Must match the PASSWORD_REGEX");
		}
		this.email = email;
		this.lastUpdateDate = new Date();
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) throws IllegalArgumentException, InstantiationException {
		if (!Pattern.matches("^" + PASSWORD_REGEX + "$", password)) {
			throw new IllegalArgumentException("Invalid password value. Must match the PASSWORD_REGEX");
		}
		try {
			MessageDigest mdi = MessageDigest.getInstance("SHA-256");
			this.password = new String(mdi.digest(password.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
			this.lastUpdateDate = new Date();
		} catch (NoSuchAlgorithmException e) {
			throw new InstantiationException("Invalid hash algorithm. Must exist for the MessageDigest class");
		}
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public UserInfo info() {
		return new UserInfo(this.id, this.name, this.surname, this.email);
	}
}