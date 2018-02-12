package com.mafafo.netfloristfrontend.controller;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mafafo.netfloristbackend.dao.CategoryDAO;
import com.mafafo.netfloristbackend.dao.ProductDAO;
import com.mafafo.netfloristbackend.dto.Category;
import com.mafafo.netfloristbackend.dto.Product;
import com.mafafo.netfloristfrontend.exception.ProductNotFoundException;

@Controller
public class PageController {

	/*
	 * page controller is the master page which controls everything with in the
	 * program
	 * 
	 */

	private static final Logger logger = LoggerFactory.getLogger(PageController.class);

	// auto wires the category and products
	@Autowired
	private CategoryDAO categoryDAO;
	@Autowired
	private ProductDAO productDAO;

	// gets the location for the home page which can be refered as index or home
	@RequestMapping(value = { "/", "/home", "/index" })
	public ModelAndView index(@RequestParam(name = "logout", required = false) String logout) {
		ModelAndView mv = new ModelAndView("page");
		mv.addObject("title", "Home");
		logger.info("Inside PageController index method - INFO");
		logger.debug("Inside PageController index method - DEBUG");

		// passing the list of categories
		mv.addObject("categories", categoryDAO.list());
		// if the logout is not null then successfully logged out.
		if (logout != null) {
			mv.addObject("message", "You have successfully logged out!");
		}
		mv.addObject("userClickHome", true);
		return mv;
	}

	// gets the about.jsp page
	@RequestMapping(value = "/about")
	public ModelAndView about() {
		ModelAndView mv = new ModelAndView("page");
		mv.addObject("title", "About Us");
		mv.addObject("userClickAbout", true);
		return mv;
	}

	// gets the contact.jsp page via request mapping
	@RequestMapping(value = "/contact")
	public ModelAndView contact() {
		ModelAndView mv = new ModelAndView("page");
		mv.addObject("title", "Contact Us");
		mv.addObject("userClickContact", true);
		return mv;
	}

	// Methods that loads all the products and based on category
	@RequestMapping(value = "/show/all/products")
	public ModelAndView showAllProducts() {
		ModelAndView mv = new ModelAndView("page");
		mv.addObject("title", "All Products");

		// passing the list of categories
		mv.addObject("categories", categoryDAO.list());
		mv.addObject("userClickAllProducts", true);
		return mv;
	}

	@RequestMapping(value = "/show/category/{id}/products")
	public ModelAndView showCategoryProducts(@PathVariable("id") int id) {
		ModelAndView mv = new ModelAndView("page");

		// categoryDAO to fetch a single category
		Category category = null;

		category = categoryDAO.get(id);

		mv.addObject("title", category.getName());

		// passing the list of categories
		mv.addObject("categories", categoryDAO.list());

		// passing the single category object
		mv.addObject("category", category);

		mv.addObject("userClickCategoryProducts", true);
		return mv;
	}

	// Views a single product
	@RequestMapping(value = "/show/{id}/product")
	public ModelAndView showSingleProduct(@PathVariable int id) throws ProductNotFoundException {

		ModelAndView mv = new ModelAndView("page");

		Product product = productDAO.get(id);

		if (product == null)
			throw new ProductNotFoundException();

		// update the view count
		product.setViews(product.getViews() + 1);
		productDAO.update(product);

		mv.addObject("title", product.getName());
		mv.addObject("product", product);
		mv.addObject("userClickShowProduct", true);

		return mv;
	}

	// calls pages within the membership folder
	@RequestMapping(value = "/membership")
	public ModelAndView register() {
		ModelAndView mv = new ModelAndView("page");
		logger.info("Page Controller membership called!");

		return mv;
	}

	// calls the login page and checks if login details are correct or incorrect
	@RequestMapping(value = "/login")
	public ModelAndView login(@RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "logout", required = false) String logout) {
		ModelAndView mv = new ModelAndView("login");
		mv.addObject("title", "Login");
		if (error != null) {
			mv.addObject("message", "Username and Password is invalid!");
		}
		if (logout != null) {
			mv.addObject("logout", "You have logged out successfully!");
		}
		return mv;
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		// Invalidates HTTP Session, then unbinds any objects bound to it.
		// Removes the authentication from securitycontext
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}

	// denies access if the user is an unauthorized user
	@RequestMapping(value = "/access-denied")
	public ModelAndView accessDenied() {
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("errorTitle", "You don't have the authority or permission to view this page...");
		mv.addObject("errorDescription", "You are not authorized to view this page!");
		mv.addObject("title", "403 Access Denied");
		return mv;
	}
}// end of code