package com.lino.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lino.dscatalog.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
