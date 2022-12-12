package com.lino.dscatalog.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.lino.dscatalog.repositories.ProductRepository;
import com.lino.dscatalog.services.exceptions.ResourceNotFoundException;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

	@InjectMocks
	private ProductService service;

	@Mock
	private ProductRepository repository;

	private long existingById;
	private long nonExistingById;
	private long countTotalProducts;

	@BeforeEach
	void setUp() throws Exception {
		
		existingById = 1L;
		nonExistingById = 123L;
		countTotalProducts = 25L;
		
		Mockito.doNothing().when(repository).deleteById(existingById);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingById);
	}

	@Test
	public void deleteShouldDoNothingWhenIdExisting() {

		service.delete(existingById);
		
		Assertions.assertDoesNotThrow(()->{
			service.delete(existingById);
		});
		
//		Mockito.verify(repository,Mockito.times(1)).deleteById(existingById);

	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdNotExisting() {

		
		Assertions.assertThrows(ResourceNotFoundException.class, ()->{
			service.delete(nonExistingById);
		});
		
//		Mockito.verify(repository,Mockito.times(1)).deleteById(existingById);

	}

}
