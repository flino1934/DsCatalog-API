package com.lino.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lino.dscatalog.dto.CategoryDTO;
import com.lino.dscatalog.entities.Category;
import com.lino.dscatalog.repositories.CategoryRepository;
import com.lino.dscatalog.services.exceptions.DataBaseException;
import com.lino.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll() {

		List<Category> list = categoryRepository.findAll();

		// esta convertendo uma lista de category para categoryDTO
		List<CategoryDTO> listDto = list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());

		return listDto;

	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		// TODO Auto-generated method stub

		Optional<Category> obj = categoryRepository.findById(id);
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Categoria Não encontrada!!"));

		return new CategoryDTO(entity);
	}

	@Transactional(readOnly = true)
	public CategoryDTO insert(CategoryDTO dto) {

		Category entity = new Category();
		entity.setName(dto.getName());
		entity = categoryRepository.save(entity);

		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		// TODO Auto-generated method stub
		try {

			Category entity = categoryRepository.getOne(id);
			entity.setName(dto.getName());
			entity = categoryRepository.save(entity);

			return new CategoryDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id não encontrado " + id);
		}
	}

	public void delete(Long id) {
		// TODO Auto-generated method stub
		
		try {
		
		categoryRepository.deleteById(id);
		
		}catch (EmptyResultDataAccessException e) {
			// TODO: handle exception
			
			throw new ResourceNotFoundException("Id não encontrado " + id);
		}catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Violação de integridade " + id);
		}
	}

}
