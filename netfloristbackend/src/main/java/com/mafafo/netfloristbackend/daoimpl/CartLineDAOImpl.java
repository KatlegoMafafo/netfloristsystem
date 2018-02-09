package com.mafafo.netfloristbackend.daoimpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mafafo.netfloristbackend.dao.CartLineDAO;
import com.mafafo.netfloristbackend.dto.Cart;
import com.mafafo.netfloristbackend.dto.CartLine;
import com.mafafo.netfloristbackend.dto.OrderDetail;

@Repository("cartLineDAO")
@Transactional
public class CartLineDAOImpl implements CartLineDAO {

	@Autowired
	private SessionFactory sessionFactory;

	// gets cart together with product details
	@Override
	public CartLine getByCartAndProduct(int cartId, int productId) {
		String query = "FROM CartLine WHERE cartId = :cartId AND product.id = :productId";
		try {

			return sessionFactory.getCurrentSession().createQuery(query, CartLine.class).setParameter("cartId", cartId)
					.setParameter("productId", productId).getSingleResult();

		} catch (Exception ex) {
			return null;
		}

	}

	// adds cartline
	@Override
	public boolean add(CartLine cartLine) {
		try {
			sessionFactory.getCurrentSession().persist(cartLine);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	// updates cartline
	@Override
	public boolean update(CartLine cartLine) {
		try {
			sessionFactory.getCurrentSession().update(cartLine);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	// removes item from cartline
	@Override
	public boolean remove(CartLine cartLine) {
		try {
			sessionFactory.getCurrentSession().delete(cartLine);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	// lists cartline
	@Override
	public List<CartLine> list(int cartId) {
		String query = "FROM CartLine WHERE cartId = :cartId";
		return sessionFactory.getCurrentSession().createQuery(query, CartLine.class).setParameter("cartId", cartId)
				.getResultList();
	}

	// gets the cartline id
	@Override
	public CartLine get(int id) {
		return sessionFactory.getCurrentSession().get(CartLine.class, Integer.valueOf(id));
	}

	// updates cart
	@Override
	public boolean updateCart(Cart cart) {
		try {
			sessionFactory.getCurrentSession().update(cart);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	// list avaible orders
	@Override
	public List<CartLine> listAvailable(int cartId) {
		String query = "FROM CartLine WHERE cartId = :cartId AND available = :available";
		return sessionFactory.getCurrentSession().createQuery(query, CartLine.class).setParameter("cartId", cartId)
				.setParameter("available", true).getResultList();
	}

	// gets the order details
	@Override
	public boolean addOrderDetail(OrderDetail orderDetail) {
		try {
			sessionFactory.getCurrentSession().persist(orderDetail);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

}
