package com.mafafo.netfloristbackend.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mafafo.netfloristbackend.dao.CategoryDAO;
import com.mafafo.netfloristbackend.dto.Category;

@Repository("categoryDAO")
public class CategoryDAOImpl implements CategoryDAO {

	
	private static List<Category> categories = new ArrayList<>();

	static {

		Category category = new Category();

		category.setId(1);
		category.setName("Valentines");
		category.setDescription("asdasd");
		category.setImageURL("image1.png");
		categories.add(category);

		category = new Category();
		category.setId(2);
		category.setName("Birthday");
		category.setDescription("asdasd");
		category.setImageURL("image2.png");
		categories.add(category);

		category = new Category();
		category.setId(3);
		category.setName("Occasions");
		category.setDescription("asdasd");
		category.setImageURL("image3.png");

		categories.add(category);
	}
	
	@Override
	public List<Category> list() {
		// TODO Auto-generated method stub
		return categories;
	}

	@Override
	public Category get(int id) {
		 //enchanced for loop
		for(Category category : categories){
			
			if(category.getId() == id) return category;
		}
		return null;
	}

}
