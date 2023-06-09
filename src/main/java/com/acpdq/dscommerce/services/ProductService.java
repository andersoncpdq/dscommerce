package com.acpdq.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.acpdq.dscommerce.dto.CategoryDTO;
import com.acpdq.dscommerce.dto.ProductDTO;
import com.acpdq.dscommerce.dto.ProductMinDTO;
import com.acpdq.dscommerce.entities.Category;
import com.acpdq.dscommerce.entities.Product;
import com.acpdq.dscommerce.repositories.ProductRepository;
import com.acpdq.dscommerce.services.exceptions.DatabaseException;
import com.acpdq.dscommerce.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		return new ProductDTO( repository.findById(id).orElseThrow( 
					() -> new ResourceNotFoundException("Recurso não encontrado") ) );
	}
	
	@Transactional(readOnly = true)
	public Page<ProductMinDTO> findAll(String name, Pageable pageable) {
		Page<Product> result = repository.searchByName(name, pageable);
		return result.map(x -> new ProductMinDTO(x));
	}
	
	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		copyDtoToEntity(dto, entity);
		return new ProductDTO( repository.save(entity) );
	}
	
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {		
			Product entity = repository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			return new ProductDTO( repository.save(entity) );
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if( !repository.existsById(id) )
			throw new ResourceNotFoundException("Recurso não encontrado");
		
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade referencial");
		}
	}

	private void copyDtoToEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setImgUrl(dto.getImgUrl());
		entity.setPrice(dto.getPrice());
		
		entity.getCategories().clear();
		for (CategoryDTO catDto : dto.getCategories()) {
			Category category = new Category();
			category.setId( catDto.getId() );
			entity.getCategories().add(category);
		}
	}
}
