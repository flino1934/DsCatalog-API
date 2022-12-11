package com.lino.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lino.dscatalog.dto.CategoryDTO;
import com.lino.dscatalog.dto.ProductDTO;
import com.lino.dscatalog.entities.Category;
import com.lino.dscatalog.entities.Product;
import com.lino.dscatalog.repositories.CategoryRepository;
import com.lino.dscatalog.repositories.ProductRepository;
import com.lino.dscatalog.services.exceptions.DataBaseException;
import com.lino.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(Pageable pageable) {

		Page<Product> list = productRepository.findAll(pageable);

		// esta convertendo uma lista de Product para ProductDTO
		return list.map(x -> new ProductDTO(x));

	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		// TODO Auto-generated method stub

		Optional<Product> obj = productRepository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Produto Não encontrado!!"));

		return new ProductDTO(entity, entity.getCategories());// osegundo parametro que esta sendo passado vai acessar a lista de categorias e passar o parametro
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {

		Product entity = new Product();
		copyDtoToEntity(dto,entity);
		entity = productRepository.save(entity);

		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		// TODO Auto-generated method stub
		try {

			Product entity = productRepository.getOne(id);
			copyDtoToEntity(dto,entity);
			entity = productRepository.save(entity);

			return new ProductDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id não encontrado " + id);
		}
	}
	
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
		try {
		
		productRepository.deleteById(id);
		
		}catch (EmptyResultDataAccessException e) {
			// TODO: handle exception
			
			throw new ResourceNotFoundException("Id não encontrado " + id);
		}catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Violação de integridade " + id);
		}
	}

	private void copyDtoToEntity(ProductDTO dto, Product entity) {
		// TODO Auto-generated method stub

		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setDate(dto.getDate());
		entity.setImgUrl(dto.getImgUrl());
		entity.setPrice(dto.getPrice());
		
		entity.getCategories().clear();
		
		//vamos fazer um foreach que vai percorrer todas as categorias que estão associados ao meu dto
		for (CategoryDTO catDto : dto.getCategories()) {
			//vai instaciar uma entidade de categorydto sem tocar no BD
			Category category = categoryRepository.getOne(catDto.getId());//vai estar pegando a categoria
			entity.getCategories().add(category);
		}
		
		
	}

}
