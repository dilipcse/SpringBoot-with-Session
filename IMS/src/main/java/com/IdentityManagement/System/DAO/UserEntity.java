package com.IdentityManagement.System.DAO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="userinfo")
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	int id;
	@Column(name="name")
	String name;
	@Column(name="password")
	String password;
	@Column(name="email")
	String email;
	
	public UserEntity() {
		
	}
	
	public UserEntity(String name, String password, String email) {
		this.name = name;
		this.password = password;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", name=" + name + ", password=" + password + ", email=" + email + "]";
	}
	
	
}
