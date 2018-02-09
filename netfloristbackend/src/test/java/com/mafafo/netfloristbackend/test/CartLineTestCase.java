package com.mafafo.netfloristbackend.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.mafafo.netfloristbackend.dao.CartLineDAO;
import com.mafafo.netfloristbackend.dao.ProductDAO;
import com.mafafo.netfloristbackend.dao.UserDAO;
import com.mafafo.netfloristbackend.dto.Cart;
import com.mafafo.netfloristbackend.dto.CartLine;
import com.mafafo.netfloristbackend.dto.User;

public class CartLineTestCase {

	// private fields
	private static AnnotationConfigApplicationContext context;
	private static CartLineDAO cartLineDAO;
	private static ProductDAO productDAO;
	private static UserDAO userDAO;
	private CartLine cartLine = null;

	@BeforeClass
	public static void init() {
		context = new AnnotationConfigApplicationContext();
		context.scan("com.mafafo.netfloristbackend");
		context.refresh();
		cartLineDAO = (CartLineDAO) context.getBean("cartLineDAO");
		productDAO = (ProductDAO) context.getBean("productDAO");
		userDAO = (UserDAO) context.getBean("userDAO");
	}

	// unit testing if user can update cartline
	@Test
	public void testUpdateCartLine() {
		// fetch the user and then cart of that user
		User user = userDAO.getByEmail("katlego96t@gmail.com");
		Cart cart = user.getCart();

		cartLine = cartLineDAO.getByCartAndProduct(cart.getId(), 2);

		cartLine.setProductCount(cartLine.getProductCount() + 1);

		double oldTotal = cartLine.getTotal();

		cartLine.setTotal(cartLine.getProduct().getUnitPrice() * cartLine.getProductCount());

		cart.setGrandTotal(cart.getGrandTotal() + (cartLine.getTotal() - oldTotal));

		assertEquals("Failed to update the CartLine!", true, cartLineDAO.update(cartLine));

	}

}
//