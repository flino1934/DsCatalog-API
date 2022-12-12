package com.lino.dscatalog.factory;

import java.time.Instant;

import com.lino.dscatalog.dto.ProductDTO;
import com.lino.dscatalog.entities.Category;
import com.lino.dscatalog.entities.Product;

public class ProductFactory {
	
	public static Product createProduct() {
		
		Product product = new Product(1L,"Phone","Good Phone",800.0,"https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/4-big.jpg",Instant.parse("2020-07-14T10:00:00Z"));
		product.getCategories().add(new Category(1L, "Eletronics"));
		return product;
	
	}
	
	public static ProductDTO createProductDTO() {
		Product product = createProduct();
		return new ProductDTO(product,product.getCategories());
	
	}

}
