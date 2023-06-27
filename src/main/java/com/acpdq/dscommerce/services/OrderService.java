package com.acpdq.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acpdq.dscommerce.dto.OrderDTO;
import com.acpdq.dscommerce.repositories.OrderRepository;
import com.acpdq.dscommerce.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	@Transactional(readOnly = true)
	public OrderDTO findById(Long id) {
		return new OrderDTO( repository.findById(id).orElseThrow( 
					() -> new ResourceNotFoundException("Recurso não encontrado") ) );
	}
}
