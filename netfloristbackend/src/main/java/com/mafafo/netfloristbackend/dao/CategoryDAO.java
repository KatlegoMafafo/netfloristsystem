package com.mafafo.netfloristbackend.dao;

import java.util.List;

import com.mafafo.netfloristbackend.dto.Category;

public interface CategoryDAO {

	List<Category> list();
	Category get(int id);

}
