package com.lino.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

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

	@Test
	public void deleteShouldThrowEmptyResutDataAcessExceptionWhenIdDoesNotExist() {

		long existingById = 132L;

		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			productRepository.deleteById(existingById);
		});

	}

}
