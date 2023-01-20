package com.masai.model;

import lombok.Data;

@Data
public class ProductDTO {
	
	
	private Integer id;
    private String name;
    private Integer categoryId;
    private Double price;
    private Double weight;
    private String description;
    private String imageName;

}
