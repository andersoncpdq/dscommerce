package com.acpdq.dscommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.acpdq.dscommerce.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
