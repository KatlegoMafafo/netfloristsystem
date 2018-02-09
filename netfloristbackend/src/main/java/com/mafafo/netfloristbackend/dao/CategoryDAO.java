package com.mafafo.netfloristbackend.dao;

import java.util.List;

import com.mafafo.netfloristbackend.dto.Category;

public interface CategoryDAO {
	
	//add, updates and deletes category
	Category get(int id);
	List<Category> list();
	boolean add(Category category);
	boolean update(Category category);
	boolean delete(Category category);

}
