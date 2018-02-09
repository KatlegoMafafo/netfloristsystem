package com.mafafo.netfloristbackend.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.mafafo.netfloristbackend.dao.ProductDAO;
import com.mafafo.netfloristbackend.dto.Product;

public class ProductTestCase {

	private static AnnotationConfigApplicationContext context;
	private static ProductDAO productDAO;
	private Product product;

	@BeforeClass
	public static void init() {
		context = new AnnotationConfigApplicationContext();
		context.scan("com.mafafo.netfloristbackend");
		context.refresh();
		productDAO = (ProductDAO) context.getBean("productDAO");
	}

	@Test
	public void testListActiveProducts() {
		assertEquals("Something went wrong while fetching the list of products!", 5,
				productDAO.listActiveProducts().size());
	}

	@Test
	public void testListActiveProductsByCategory() {
		assertEquals("Something went wrong while fetching the list of products!", 3,
				productDAO.listActiveProductsByCategory(3).size());
		assertEquals("Something went wrong while fetching the list of products!", 2,
				productDAO.listActiveProductsByCategory(1).size());
	}

	@Test
	public void testGetLatestActiveProduct() {
		assertEquals("Something went wrong while fetching the list of products!", 3,
				productDAO.getLatestActiveProducts(3).size());

	}

} // end of code
