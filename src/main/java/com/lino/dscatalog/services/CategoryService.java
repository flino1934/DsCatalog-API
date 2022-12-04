package com.lino.dscatalog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lino.dscatalog.dto.CategoryDTO;
import com.lino.dscatalog.entities.Category;
import com.lino.dscatalog.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll() {

		List<Category> list = categoryRepository.findAll();

		// esta convertendo uma lista de category para categoryDTO
		List<CategoryDTO> listDto = list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());

//		List<CategoryDTO> listDto = new ArrayList<>();
//
//		for (Category cat : list) {
//
//			listDto.add(new CategoryDTO(cat));// esta convertendo uma lista de category para categoryDTO
//
//		}

		return listDto;

	}

}
