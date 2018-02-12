package com.mafafo.netfloristfrontend.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.mafafo.netfloristbackend.dao.UserDAO;
import com.mafafo.netfloristbackend.dto.Address;
import com.mafafo.netfloristbackend.dto.Cart;
import com.mafafo.netfloristbackend.dto.User;
import com.mafafo.netfloristfrontend.model.RegisterModel;

@Component
public class RegisterHandler {

	// auto wires password encoder and user DAO
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserDAO userDAO;

	public RegisterModel init() {
		return new RegisterModel();
	}

	// set user to register
	public void addUser(RegisterModel registerModel, User user) {
		registerModel.setUser(user);
	}

	// sets register billing
	public void addBilling(RegisterModel registerModel, Address billing) {
		registerModel.setBilling(billing);
	}

	// validates password
	public String validateUser(User user, MessageContext error) {
		String transitionValue = "success";
		if (!user.getPassword().equals(user.getConfirmPassword())) {
			error.addMessage(new MessageBuilder().error().source("confirmPassword")
					.defaultText("Password does not match confirm password!").build());
			transitionValue = "failure";
		}
		// validates the email to check if it exsist in the database
		if (userDAO.getByEmail(user.getEmail()) != null) {
			error.addMessage(new MessageBuilder().error().source("email").defaultText("Email address is already taken!")
					.build());
			transitionValue = "failure";
		}
		return transitionValue;
	}

	// saves registration and gets user role
	public String saveAll(RegisterModel registerModel) {
		String transitionValue = "success";
		User user = registerModel.getUser();
		if (user.getRole().equals("USER")) {
			// create a new cart
			Cart cart = new Cart();
			cart.setUser(user);
			user.setCart(cart);
		}

		// encode the password
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		// save the user
		userDAO.add(user);
		// save the billing address
		Address billing = registerModel.getBilling();
		billing.setUserId(user.getId());
		billing.setBilling(true);
		userDAO.addAddress(billing);
		return transitionValue;
	}
} // end of code
