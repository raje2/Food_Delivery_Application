package com.masai.service;

import java.util.List;

import com.masai.model.Category;

public interface CategoryService {
	
	public Category addCategory(Category category);
	
	public List<Category> getCategoryList();
	
	public Category deleteCategory(Integer id);
	
	public Category finCategoryById(Integer id);

}
