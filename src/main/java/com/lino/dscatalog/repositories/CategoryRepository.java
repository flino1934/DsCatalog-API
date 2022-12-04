package com.lino.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lino.dscatalog.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
