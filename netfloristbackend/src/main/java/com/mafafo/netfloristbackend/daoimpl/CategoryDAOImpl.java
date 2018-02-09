package com.mafafo.netfloristbackend.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mafafo.netfloristbackend.dao.CategoryDAO;
import com.mafafo.netfloristbackend.dto.Category;

@Repository("categoryDAO")
@Transactional
public class CategoryDAOImpl implements CategoryDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private static List<Category> categories = new ArrayList<>();

	// selects the categories with an active status
	@Override
	public List<Category> list() {

		String selectActiveCategory = "FROM Category WHERE active = :active";
		Query query = sessionFactory.getCurrentSession().createQuery(selectActiveCategory);
		query.setParameter("active", true);
		return query.getResultList();
	}

	// Getting a single category using the category id
	@Override
	public Category get(int id) {
		return sessionFactory.getCurrentSession().get(Category.class, Integer.valueOf(id));
	}

	@Override
	// add category to database
	public boolean add(Category category) {
		try {
			// add the category to the database table
			sessionFactory.getCurrentSession().persist(category);
			return true;

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	// updating a single category
	@Override
	public boolean update(Category category) {
		try {
			// add the category to the database table
			sessionFactory.getCurrentSession().update(category);
			return true;

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	//delete category
	@Override
	public boolean delete(Category category) {

		category.setActive(false);

		try {
			// add the category to the database table
			sessionFactory.getCurrentSession().update(category);
			return true;

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

} // end code
