package it.unimib.sd2024.models;

public class UserInfo {
	public Long id;
	public String name;
	public String surname;
	public String email;

	public UserInfo(Long id, String name, String surname, String email) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
	}
}
