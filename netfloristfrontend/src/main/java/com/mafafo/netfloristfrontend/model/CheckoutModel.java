package com.mafafo.netfloristfrontend.model;

import java.io.Serializable;
import java.util.List;

import com.mafafo.netfloristbackend.dto.Address;
import com.mafafo.netfloristbackend.dto.Cart;
import com.mafafo.netfloristbackend.dto.CartLine;
import com.mafafo.netfloristbackend.dto.OrderDetail;
import com.mafafo.netfloristbackend.dto.User;

public class CheckoutModel implements Serializable {

	private static final long serialVersionUID = 1L;

	// private fields
	private User user;
	private Address shipping;
	private Cart cart;
	private List<CartLine> cartLines;
	private OrderDetail orderDetail;
	private double checkoutTotal;

	// gets order details
	public OrderDetail getOrderDetail() {
		return orderDetail;
	}

	// sets order details
	public void setOrderDetail(OrderDetail orderDetail) {
		this.orderDetail = orderDetail;
	}

	// get shopping cart
	public Cart getCart() {
		return cart;
	}

	// sets cart
	public void setCart(Cart cart) {
		this.cart = cart;
	}

	// gets checkout total
	public double getCheckoutTotal() {
		return checkoutTotal;
	}

	// set checkout total
	public void setCheckoutTotal(double checkoutTotal) {
		this.checkoutTotal = checkoutTotal;
	}

	// list the cartline
	public List<CartLine> getCartLines() {
		return cartLines;
	}

	// sets the cartline
	public void setCartLines(List<CartLine> cartLines) {
		this.cartLines = cartLines;
	}

	// gets the user
	public User getUser() {
		return user;
	}

	// sets the user
	public void setUser(User user) {
		this.user = user;
	}

	// gets delivery address
	public Address getShipping() {
		return shipping;
	}

	// sets delivery address
	public void setShipping(Address shipping) {
		this.shipping = shipping;
	}
} // end of code
