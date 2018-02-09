package com.mafafo.netfloristbackend.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.mafafo.netfloristbackend.dao.CategoryDAO;
import com.mafafo.netfloristbackend.dto.Category;

public class CategoryTestCase {

	// private fields
	private static AnnotationConfigApplicationContext context;
	private static CategoryDAO categoryDAO;
	private Category category;

	@BeforeClass
	public static void init() {
		context = new AnnotationConfigApplicationContext();
		context.scan("com.mafafo.netfloristbackend");
		context.refresh();
		categoryDAO = (CategoryDAO) context.getBean("categoryDAO");
	}

	// tests if create, update, read and delete works
	@Test
	public void testCRUDCategory() {

		// add operation
		category = new Category();

		category.setName("Birthday");
		category.setDescription("This is some description for birthday!");
		category.setImageURL("CAT_2.png");
		assertEquals("Successfully added a category inside the table!", true, categoryDAO.add(category));

		category = new Category();

		category.setName("Valentine");
		category.setDescription("This is some description for Valentine!");
		category.setImageURL("CAT_1.png");

		assertEquals("Successfully added a category inside the table!", true, categoryDAO.add(category));

		// fetching and updating the category
		category = categoryDAO.get(2);
		category.setName("valentines");
		assertEquals("Successfully updated a single category in the table!", true, categoryDAO.update(category));

		// delete the category
		assertEquals("Successfully deleted a single category in the table!", true, categoryDAO.delete(category));
		// fetching the list
		assertEquals("Successfully fetched the list of categories from the table!", 1, categoryDAO.list().size());

	}

}
