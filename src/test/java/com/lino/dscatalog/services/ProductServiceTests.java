package com.lino.dscatalog.services;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.lino.dscatalog.dto.ProductDTO;
import com.lino.dscatalog.entities.Product;
import com.lino.dscatalog.factory.ProductFactory;
import com.lino.dscatalog.repositories.ProductRepository;
import com.lino.dscatalog.services.exceptions.DataBaseException;
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
	private Long dependentId;
	private  PageImpl<Product> page;
	private Product product;

	@BeforeEach
	void setUp() throws Exception {
		
		existingById = 1L;
		nonExistingById = 123L;
		countTotalProducts = 25L;
		dependentId = 4L;
		product = ProductFactory.createProduct();
		page = new PageImpl<>(List.of(product));
		
		Mockito.when(repository.findAll((Pageable)ArgumentMatchers.any())).thenReturn(page);
		
		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);
		
		Mockito.when(repository.findById(existingById)).thenReturn(Optional.of(product));
		Mockito.when(repository.findById(nonExistingById)).thenReturn(Optional.empty());
		
		Mockito.doNothing().when(repository).deleteById(existingById);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingById);
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
	
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
	
	@Test
	public void deleteShouldThrowDataBaseExceptionWhenDoesIDoesNotExisting() {

		
		Assertions.assertThrows(DataBaseException.class, ()->{
			service.delete(dependentId);
		});
		
//		Mockito.verify(repository,Mockito.times(1)).deleteById(existingById);

	}
	
	@Test
	public void findAllPagedShouldReturnPage() {
		
		Pageable pageable = PageRequest.of(0, 10);
		
		Page<ProductDTO> result = service.findAllPaged(pageable);
		
		Assertions.assertNotNull(result);
		
		Mockito.verify(repository).findAll(pageable);
		
	}

}
