package com.masai.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.model.Category;
import com.masai.repository.CategoryRepo;

@Service
public class CategorySerImpl implements CategoryService{
	
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public Category addCategory(Category category) {
		
			return categoryRepo.save(category);
	}

	@Override
	public List<Category> getCategoryList() {
		
		return categoryRepo.findAll();
	}

	@Override
	public Category deleteCategory(Integer id) {
		
		Optional<Category> findCate =  categoryRepo.findById(id);
		Category category = null;
		
		if(findCate.isPresent()) {
			categoryRepo.deleteById(id);
			category =  findCate.get();
		}
		else {
			category = null;
		}
		return category;
	}

	@Override
	public Category finCategoryById(Integer id) {
		Optional<Category> findCat = categoryRepo.findById(id);
		
		Category cat = null;
		if(findCat.isPresent()) {
			cat = findCat.get();
		}
		else {
			cat = null;
		}
		return cat;
	}

	

}
