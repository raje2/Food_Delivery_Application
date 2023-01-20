package com.masai.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.masai.global.GlobalData;
import com.masai.model.Product;
import com.masai.service.CategoryService;
import com.masai.service.ProductService;

@Controller
public class HomeController {
	
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;
	
	@GetMapping({"/" , "/home"})
	public String home(Model model) {
		
		model.addAttribute("cartCount",GlobalData.cart.size());
		return "index";
	}
	
	@GetMapping("/shop")
	public String shop(Model model) {
		
		model.addAttribute("cartCount",GlobalData.cart.size());
		model.addAttribute("categories",categoryService.getCategoryList());
		model.addAttribute("products",productService.getProductList());
		
		return "shop";
	}
	
	@GetMapping("/shop/category/{id}")
     public String shop(@PathVariable Integer id, Model model) {
		
		List<Product> productList =  productService.getProductsByCategoryId(id);
		
		model.addAttribute("products",productList);
		model.addAttribute("categories",categoryService.getCategoryList());
		model.addAttribute("cartCount",GlobalData.cart.size());
		
		return "shop";
	}
	
	@GetMapping("/shop/viewproduct/{id}")
    public String viewProduct(@PathVariable Integer id, Model model) {
		
		Product product = productService.getProductById(id);
		
		model.addAttribute("cartCount",GlobalData.cart.size());
		model.addAttribute("product",product);
		model.addAttribute("categories",categoryService.getCategoryList());
		
		return "viewProduct";
	}

}
