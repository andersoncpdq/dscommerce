package com.acpdq.dscommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.acpdq.dscommerce.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
