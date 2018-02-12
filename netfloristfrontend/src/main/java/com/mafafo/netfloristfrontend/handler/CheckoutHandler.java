package com.mafafo.netfloristfrontend.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mafafo.netfloristbackend.dao.CartLineDAO;
import com.mafafo.netfloristbackend.dao.ProductDAO;
import com.mafafo.netfloristbackend.dao.UserDAO;
import com.mafafo.netfloristbackend.dto.Address;
import com.mafafo.netfloristbackend.dto.Cart;
import com.mafafo.netfloristbackend.dto.CartLine;
import com.mafafo.netfloristbackend.dto.OrderDetail;
import com.mafafo.netfloristbackend.dto.OrderItem;
import com.mafafo.netfloristbackend.dto.Product;
import com.mafafo.netfloristbackend.dto.User;
import com.mafafo.netfloristfrontend.model.UserModel;
import com.mafafo.netfloristfrontend.util.FileUtil;
import com.mafafo.netfloristfrontend.model.CheckoutModel;

@Component
public class CheckoutHandler {

	private static final Logger logger = LoggerFactory.getLogger(CheckoutHandler.class);

	// Autowires DAO classes
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private CartLineDAO cartLineDAO;
	@Autowired
	private HttpSession session;

	public CheckoutModel init(String name) throws Exception {

		User user = userDAO.getByEmail(name);
		CheckoutModel checkoutModel = null;

		if (user != null) {
			checkoutModel = new CheckoutModel();
			checkoutModel.setUser(user);
			checkoutModel.setCart(user.getCart());

			double checkoutTotal = 0.00;
			List<CartLine> cartLines = cartLineDAO.listAvailable(user.getCart().getId());

			if (cartLines.size() == 0) {
				throw new Exception("There are no products available for checkout now!");
			}
			for (CartLine cartLine : cartLines) {
				checkoutTotal += cartLine.getTotal();
			}
			checkoutModel.setCartLines(cartLines);
			checkoutModel.setCheckoutTotal(checkoutTotal);
		}

		return checkoutModel;
	}

	// gets the delivery address
	public List<Address> getShippingAddresses(CheckoutModel model) {

		List<Address> addresses = userDAO.listShippingAddresses(model.getUser().getId());

		if (addresses.size() == 0) {
			addresses = new ArrayList<>();
		}

		addresses.add(addresses.size(), userDAO.getBillingAddress(model.getUser().getId()));

		return addresses;
	}

	public String saveAddressSelection(CheckoutModel checkoutModel, int shippingId) {

		String transitionValue = "success";

		// logger.info(String.valueOf(shippingId));

		Address shipping = userDAO.getAddress(shippingId);

		checkoutModel.setShipping(shipping);

		return transitionValue;

	}

	public String saveAddress(CheckoutModel checkoutModel, Address shipping) {

		String transitionValue = "success";

		// set the user id
		// set shipping as true
		shipping.setUserId(checkoutModel.getUser().getId());
		shipping.setShipping(true);
		userDAO.addAddress(shipping);

		// set the shipping id to flowScope object
		checkoutModel.setShipping(shipping);

		return transitionValue;

	}

	public String saveOrder(CheckoutModel checkoutModel) {
		String transitionValue = "success";

		// create a new order object
		OrderDetail orderDetail = new OrderDetail();

		// attach the user
		orderDetail.setUser(checkoutModel.getUser());

		// attach the shipping address
		orderDetail.setShipping(checkoutModel.getShipping());

		// fetch the billing address
		Address billing = userDAO.getBillingAddress(checkoutModel.getUser().getId());
		orderDetail.setBilling(billing);

		List<CartLine> cartLines = checkoutModel.getCartLines();
		OrderItem orderItem = null;

		double orderTotal = 0.00;
		int orderCount = 0;
		Product product = null;

		for (CartLine cartLine : cartLines) {

			orderItem = new OrderItem();

			orderItem.setBuyingPrice(cartLine.getBuyingPrice());
			orderItem.setProduct(cartLine.getProduct());
			orderItem.setProductCount(cartLine.getProductCount());
			orderItem.setTotal(cartLine.getTotal());

			orderItem.setOrderDetail(orderDetail);
			orderDetail.getOrderItems().add(orderItem);

			orderTotal += orderItem.getTotal();
			orderCount++;

			// update the product
			// reduce the quantity of product
			product = cartLine.getProduct();
			product.setQuantity(product.getQuantity() - cartLine.getProductCount());
			product.setPurchases(product.getPurchases() + cartLine.getProductCount());
			productDAO.update(product);

			// delete the cartLine
			cartLineDAO.remove(cartLine);

		}

		orderDetail.setOrderTotal(orderTotal);
		orderDetail.setOrderCount(orderCount);
		orderDetail.setOrderDate(new Date());

		// save the order
		cartLineDAO.addOrderDetail(orderDetail);

		// set it to the orderDetails of checkoutModel
		checkoutModel.setOrderDetail(orderDetail);

		// update the cart
		Cart cart = checkoutModel.getCart();
		cart.setGrandTotal(cart.getGrandTotal() - orderTotal);
		cart.setCartLines(cart.getCartLines() - orderCount);
		cartLineDAO.updateCart(cart);

		// update the cart if its in the session
		UserModel userModel = (UserModel) session.getAttribute("userModel");
		if (userModel != null) {
			userModel.setCart(cart);
		}

		return transitionValue;
	}

	public OrderDetail getOrderDetail(CheckoutModel checkoutModel) {
		return checkoutModel.getOrderDetail();
	}
} // end of code
