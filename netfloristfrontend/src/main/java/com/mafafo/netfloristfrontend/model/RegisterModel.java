package com.mafafo.netfloristfrontend.model;

import java.io.Serializable;

import com.mafafo.netfloristbackend.dto.Address;
import com.mafafo.netfloristbackend.dto.User;

public class RegisterModel implements Serializable {

	private static final long serialVersionUID = 1L;
	// private fields
	private User user;
	private Address billing;

	// setters and getters
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Address getBilling() {
		return billing;
	}

	public void setBilling(Address billing) {
		this.billing = billing;
	}
} // end of code
