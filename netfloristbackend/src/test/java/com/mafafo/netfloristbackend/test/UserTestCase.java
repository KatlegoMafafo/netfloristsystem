package com.mafafo.netfloristbackend.test;

import org.junit.BeforeClass;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.mafafo.netfloristbackend.dao.UserDAO;
import com.mafafo.netfloristbackend.dto.Address;
import com.mafafo.netfloristbackend.dto.Cart;
import com.mafafo.netfloristbackend.dto.User;

public class UserTestCase {

	private static AnnotationConfigApplicationContext context;
	private static UserDAO userDAO;
	private User user = null;
	private Cart cart = null;
	private Address address = null;

	@BeforeClass
	public static void init() {
		context = new AnnotationConfigApplicationContext();
		context.scan("com.mafafo.netfloristbackend");
		context.refresh();

		userDAO = (UserDAO) context.getBean("userDAO");
	}

} // end of code
