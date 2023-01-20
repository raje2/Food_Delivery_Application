package com.masai.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.model.Product;
import com.masai.repository.ProductRepo;

@Service
public class ProductsSerImpl implements ProductService{
	
	@Autowired
	private ProductRepo productRepo;

	@Override
	public Product addProduct(Product product) {
		
		return productRepo.save(product);
	}

	@Override
	public List<Product> getProductList() {
		
		return productRepo.findAll();
	}

	@Override
	public Product removeProductById(Integer id) {
		Product prod = null;
		
		Optional<Product> findProduct = productRepo.findById(id);
		
		if(findProduct.isPresent()) {
			productRepo.deleteById(id);
			prod = findProduct.get();
		}
		else {
			prod = null;
		}
		return prod;
	}

	@Override
	public Product getProductById(Integer id) {
		Optional<Product> findProduct = productRepo.findById(id);
		return findProduct.get();
	}

	@Override
	public List<Product> getProductsByCategoryId(Integer id) {
		
		List<Product> productList = productRepo.findAll();
		
		List<Product> list = new ArrayList<>();
		
		if(productList.size()>0) {
			
			for(Product i:productList) {
				
				if(i.getCategory().getId()==id) {
					list.add(i);
				}
			}
			
		}
		
		return list;
		
	}

	

}
