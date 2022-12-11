package com.lino.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.lino.dscatalog.entities.Product;

@DataJpaTest
public class ProductRepositoryTest {

	@Autowired
	private ProductRepository productRepository;

	private long existingById;
	private long nonExistingById;
	
	
	@BeforeEach
	void setUp() throws Exception{
		existingById = 1L;
		nonExistingById = 123;
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
	
}
