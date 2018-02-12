package com.mafafo.netfloristfrontend.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

	// displays if the page is unavailable
	@ExceptionHandler(NoHandlerFoundException.class)
	public ModelAndView handlerNoHandlerFoundException() {

		ModelAndView mv = new ModelAndView("error");
		mv.addObject("errorTitle", "The page is not constructed!");
		mv.addObject("errorDescription", "The page you are looking for is currently unavailable!");
		mv.addObject("title", "404 Error Page");
		return mv;
	}

	// displays when product is not available
	@ExceptionHandler(ProductNotFoundException.class)
	public ModelAndView handlerProductNotFoundException() {

		ModelAndView mv = new ModelAndView("error");
		mv.addObject("errorTitle", "Product not available!");
		mv.addObject("errorDescription", "The product you are looking for is currently unavailable!");
		mv.addObject("title", "Product Unavailable");
		return mv;
	}

	@ExceptionHandler(Exception.class)
	public ModelAndView handlerException(Exception ex) {

		ModelAndView mv = new ModelAndView("error");
		mv.addObject("errorTitle", "Contact Administrator, on 0837782696!");

		// only for debugging the application
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		mv.addObject("errorDescription", sw.toString());
		mv.addObject("title", "Error");
		return mv;
	}
} // end of code
