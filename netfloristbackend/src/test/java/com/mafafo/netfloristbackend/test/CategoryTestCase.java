package com.mafafo.netfloristbackend.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.mafafo.netfloristbackend.dao.CategoryDAO;
import com.mafafo.netfloristbackend.dto.Category;

public class CategoryTestCase {
	
	private static AnnotationConfigApplicationContext context;
	
	private static CategoryDAO categoryDAO;
	
	private Category category;
	
	@BeforeClass
	public static void init(){
		context = new AnnotationConfigApplicationContext();
		context.scan("com.mafafo.netfloristbackend");
		context.refresh();
		categoryDAO = (CategoryDAO)context.getBean("categoryDAO");
	}
	
	/*@Test
	public void testAddCategory(){
		
		category = new Category();
		
		category.setName("Valentines");
		category.setDescription("asdasd");
		category.setImageURL("image1.png");
		assertEquals("Sucessfuly added a category inside the table!", true,categoryDAO.add(category));
	} */

/*	@Test
	public void testGetCategory(){
		category = categoryDAO.get(3);
		
		assertEquals("Sucessfuly fetched a single  category inside the table!","Occation",category.getName());

	} */
	
	/*@Test
	public void testUpdateCategory(){
		category = categoryDAO.get(3);
		
		category.setName("TV");
		assertEquals("Sucessfuly updated a single  category in the table!",true,categoryDAO.update(category));

	} */
	
	@Test
	public void testDeleteCategory(){
		category = categoryDAO.get(3);
		assertEquals("Sucessfuly deleted a single  category in the table!",true,categoryDAO.delete(category));

	}
}
