package com.mafafo.netfloristfrontend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mafafo.netfloristbackend.dao.ProductDAO;
import com.mafafo.netfloristbackend.dto.Product;

@Controller
@RequestMapping("/json/data")
public class JsonDataController {

	// autowires to get beans from productDAO
	@Autowired
	private ProductDAO productDAO;

	// gets all products and return product list in Json format
	@RequestMapping("/admin/all/products")
	@ResponseBody
	public List<Product> getAllProductsList() {
		return productDAO.list();
	}

	@RequestMapping("/all/products")
	@ResponseBody
	public List<Product> getAllProducts() {
		return productDAO.listActiveProducts();
	}

	// gets the products by category
	@RequestMapping("/category/{id}/products")
	@ResponseBody
	public List<Product> getProductsByCategory(@PathVariable int id) {
		return productDAO.listActiveProductsByCategory(id);
	}

	// gets the most viewed products
	@RequestMapping("/mv/products")
	@ResponseBody
	public List<Product> getMostViewedProducts() {
		return productDAO.getProductsByParam("views", 5);
	}

	// gets the most purchased products
	@RequestMapping("/mp/products")
	@ResponseBody
	public List<Product> getMostPurchasedProducts() {
		return productDAO.getProductsByParam("purchases", 5);
	}
} // end of code
