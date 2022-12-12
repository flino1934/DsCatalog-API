package com.lino.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.lino.dscatalog.entities.Product;
import com.lino.dscatalog.factory.ProductFactory;
import com.lino.dscatalog.services.exceptions.ResourceNotFoundException;

@DataJpaTest
public class ProductRepositoryTest {

	@Autowired
	private ProductRepository productRepository;

	private long existingById;
	private long nonExistingById;
	private long countTotalProducts;
	
	@BeforeEach
	void setUp() throws Exception{
		existingById = 1L;
		nonExistingById = 123L;
		countTotalProducts = 25L;
	}
	
	@Test
	public void deleteShouldObjectWhenIdExist() {

		productRepository.deleteById(existingById);

		Optional<Product> result = productRepository.findById(existingById);
		Assertions.assertFalse(result.isPresent());

	}

	@Test
	public void deleteShouldThrowEmptyResutDataAcessExceptionWhenIdDoesNotExist() {

		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			productRepository.deleteById(nonExistingById);
		});

	}
	
	@Test
	public void saveShouldPersistWithAutoIncrementWhenIdIsNull() {
		
		Product product = ProductFactory.createProduct();
		product.setId(null);
		
		product = productRepository.save(product);
		
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalProducts+1, product.getId());
		
	}
	
	@Test
	public void getShouldProductByIdExist() {
		
		Optional<Product> obj = productRepository.findById(existingById);
		Assertions.assertTrue(obj.isPresent());
		
	}
	
	@Test
	public void getShouldProductByIdDoesNotExist() {
		
		Optional<Product> obj = productRepository.findById(nonExistingById);
		Assertions.assertTrue(obj.isEmpty());
		
	}
	
}
