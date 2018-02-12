package com.mafafo.netfloristfrontend.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mafafo.netfloristbackend.dao.CategoryDAO;
import com.mafafo.netfloristbackend.dao.ProductDAO;
import com.mafafo.netfloristbackend.dto.Product;
import com.mafafo.netfloristfrontend.util.FileUtil;
import com.mafafo.netfloristfrontend.validator.ProductValidator;
import com.mafafo.netfloristbackend.dto.Category;

@Controller
@RequestMapping("/manage")
public class ManagementController {

	private static final Logger logger = LoggerFactory.getLogger(ManagementController.class);

	// auto wires the productDAO and categoryDao
	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private CategoryDAO categoryDAO;

	// maps the products
	@RequestMapping("/product")
	public ModelAndView manageProduct(@RequestParam(name = "success", required = false) String success) {

		ModelAndView mv = new ModelAndView("page");
		mv.addObject("title", "Product Management");
		mv.addObject("userClickManageProduct", true);

		Product nProduct = new Product();

		// assuming that the user is ADMIN
		nProduct.setSupplierId(1);
		nProduct.setActive(true);
		mv.addObject("product", nProduct);

		// if statement to add product to the database
		if (success != null) {
			if (success.equals("product")) {
				mv.addObject("message", "Product submitted successfully!");
			} else if (success.equals("category")) {
				mv.addObject("message", "Category submitted successfully!");
			}
		}
		return mv;
	}

	// gets the product path for editing
	@RequestMapping("/{id}/product")
	public ModelAndView manageProductEdit(@PathVariable int id) {

		ModelAndView mv = new ModelAndView("page");
		mv.addObject("title", "Product Management");
		mv.addObject("userClickManageProduct", true);

		mv.addObject("product", productDAO.get(id));
		return mv;
	}

	@RequestMapping(value = "/product", method = RequestMethod.POST)
	public String managePostProduct(@Valid @ModelAttribute("product") Product mProduct, BindingResult results,
			Model model, HttpServletRequest request) {

		// mandatory file upload check
		if (mProduct.getId() == 0) {
			new ProductValidator().validate(mProduct, results);
		} else {
			// edit check only when the file has been selected
			if (!mProduct.getFile().getOriginalFilename().equals("")) {
				new ProductValidator().validate(mProduct, results);
			}
		}

		// validate if the product was able to be added into the database
		if (results.hasErrors()) {
			model.addAttribute("message", "Validation fails for adding the product!");
			model.addAttribute("userClickManageProduct", true);
			return "page";
		}
		if (mProduct.getId() == 0) {
			productDAO.add(mProduct);
		} else {
			productDAO.update(mProduct);
		}
		// upload the file which in this chase its a picture
		if (!mProduct.getFile().getOriginalFilename().equals("")) {
			FileUtil.uploadFile(request, mProduct.getFile(), mProduct.getCode());
		}
		return "redirect:/manage/product?success=product";
	}

	// activates or deactivate products
	@RequestMapping(value = "/product/{id}/activation", method = RequestMethod.GET)
	@ResponseBody
	public String managePostProductActivation(@PathVariable int id) {
		Product product = productDAO.get(id);
		boolean isActive = product.isActive();
		product.setActive(!isActive);
		productDAO.update(product);
		return (isActive) ? "Product Dectivated Successfully!" : "Product Activated Successfully";
	}

	@RequestMapping(value = "/category", method = RequestMethod.POST)
	public String managePostCategory(@ModelAttribute("category") Category mCategory, HttpServletRequest request) {
		categoryDAO.add(mCategory);
		return "redirect:" + request.getHeader("Referer") + "?success=category";
	}

	// return category list
	@ModelAttribute("categories")
	public List<Category> modelCategories() {
		return categoryDAO.list();
	}

	// return new categories
	@ModelAttribute("category")
	public Category modelCategory() {
		return new Category();
	}
} // end of code
