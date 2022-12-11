package com.lino.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.lino.dscatalog.entities.Product;

@DataJpaTest
public class ProductRepositoryTest {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Test
	public void deleteShouldObjectWhenIdExist() {
		
		long existingById = 1L;
		
		productRepository.deleteById(existingById);
		
		Optional<Product> result = productRepository.findById(existingById);
		Assertions.assertFalse(result.isPresent());
		
	}

}
