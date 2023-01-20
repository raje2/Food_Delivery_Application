package com.masai.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.masai.model.Category;
import com.masai.model.Product;
import com.masai.model.ProductDTO;
import com.masai.repository.ProductRepo;
import com.masai.service.CategoryService;
import com.masai.service.ProductService;

@Controller
public class AdminController {
	
	public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productsimg";
	
	@Autowired
	private CategoryService cateService;
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/admin")
	public String adminHome() {
		return "adminHome";
	}
	
   //	<--***** Category *****-->
	
	@GetMapping("/admin/categories")
	public String adminCategories(Model m) {
		List<Category> list = cateService.getCategoryList();
		m.addAttribute("categories",list);
		return "categories";
	}
	
	@GetMapping("/admin/categories/add")
	public String adminAddCategories(Model m) {
		
		m.addAttribute("category",new Category());
		
		return "categoriesAdd";
	}
	
	//create category
	@PostMapping("/admin/categories/add")
	public String adminPostCategories(@ModelAttribute("category") Category category) {
		cateService.addCategory(category);
		return "redirect:/admin/categories";
	}
	
	//delete category
	@GetMapping("/admin/categories/delete/{id}")
	public String deleteCategories(@PathVariable Integer id) {
		cateService.deleteCategory(id);
		return "redirect:/admin/categories";
	}
	
	//update category
	@GetMapping("/admin/categories/update/{id}")
	public String updateCategories(@PathVariable Integer id, Model m) {
		
		Category findCat = cateService.finCategoryById(id);
		
		m.addAttribute("category",findCat);
		
		return "categoriesAdd";
	}

	
     //	<--***** Product *****-->
	
	@GetMapping("/admin/products")
	public String adminProducts(Model m) {
		List<Product> productList = productService.getProductList();
		m.addAttribute("products",productList);
		return "products";
	}
	
	@GetMapping("/admin/products/add")
	public String adminAddProducts(Model m) {
		
		m.addAttribute("productDTO",new ProductDTO());
		m.addAttribute("categories",cateService.getCategoryList());
		
		return "productsAdd";
	}
	
	@PostMapping("/admin/products/add")
	public String addProducts(@ModelAttribute("productDTO") ProductDTO productDTO,
			                  @RequestParam("productImage")MultipartFile file,
			                  @RequestParam("imgName") String imgName) throws IOException {
		
		Product product = new Product();
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setCategory(cateService.finCategoryById(productDTO.getCategoryId()));
		product.setPrice(productDTO.getPrice());
		product.setDescription(productDTO.getDescription());
		product.setWeight(productDTO.getWeight());
		
		//for image we devide 2 for static folder productimg and for database
		
		String imageUUID;
		
		if(!file.isEmpty()) {
			imageUUID = file.getOriginalFilename();
			Path fileNameAndPath = Paths.get(uploadDir,imageUUID);
			Files.write(fileNameAndPath, file.getBytes());
		}else {
			imageUUID = imgName;
		}
		
		product.setImageName(imageUUID);
		
		productService.addProduct(product);
		
		return "redirect:/admin/products";
	}
	
	  //delete Products
		@GetMapping("/admin/product/delete/{id}")
		public String deleteProducts(@PathVariable Integer id) {
			productService.removeProductById(id);
			return "redirect:/admin/products";
		}
		
		//update category
		@GetMapping("/admin/product/update/{id}")
		public String updateProducts(@PathVariable Integer id, Model m) {
			
			Product products =  productService.getProductById(id);
			
			ProductDTO proDTO = new ProductDTO();
			proDTO.setId(products.getId());
			proDTO.setName(products.getName());
			proDTO.setCategoryId(products.getCategory().getId());
			proDTO.setDescription(products.getDescription());
			proDTO.setImageName(products.getImageName());
			proDTO.setWeight(products.getWeight());
			proDTO.setPrice(products.getPrice());
			
			m.addAttribute("categories", cateService.getCategoryList());
			m.addAttribute("productDTO",proDTO);
			
			return "productsAdd";
		}
}
