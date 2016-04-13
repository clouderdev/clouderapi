package com.clouder.clouderapi.document;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class User {

	@Id
	private String id;

	private String emailId;

	private String firstName;

	private String lastName;

	private String username;

	private String password;

	private List<Cloud> clouds;

	public User(String emailId, String firstName, String lastName, String username, String password,
			List<Cloud> clouds) {
		super();
		this.emailId = emailId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.clouds = clouds;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Cloud> getClouds() {
		return clouds;
	}

	public void setClouds(List<Cloud> clouds) {
		this.clouds = clouds;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", emailId=" + emailId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", username=" + username + ", password=" + password + ", clouds=" + clouds + "]";
	}

}
