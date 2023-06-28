package com.acpdq.dscommerce.services;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acpdq.dscommerce.dto.OrderDTO;
import com.acpdq.dscommerce.dto.OrderItemDTO;
import com.acpdq.dscommerce.entities.Order;
import com.acpdq.dscommerce.entities.OrderItem;
import com.acpdq.dscommerce.entities.OrderStatus;
import com.acpdq.dscommerce.entities.Product;
import com.acpdq.dscommerce.entities.User;
import com.acpdq.dscommerce.repositories.OrderItemRepository;
import com.acpdq.dscommerce.repositories.OrderRepository;
import com.acpdq.dscommerce.repositories.ProductRepository;
import com.acpdq.dscommerce.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Transactional(readOnly = true)
	public OrderDTO findById(Long id) {
		return new OrderDTO( repository.findById(id).orElseThrow( 
					() -> new ResourceNotFoundException("Recurso não encontrado") ) );
	}

	@Transactional
	public OrderDTO insert(OrderDTO dto) {
		
		Order order = new Order();
		order.setMoment(Instant.now());
		order.setStatus(OrderStatus.WAITING_PAYMENT);
		
		User user = userService.authenticated();
		order.setClient(user);
		
		for (OrderItemDTO itemDto : dto.getItems()) {
			Product product = productRepository.getReferenceById(itemDto.getProductId());
			OrderItem item = new OrderItem(order, product, itemDto.getQuantity(), product.getPrice());
			order.getItems().add(item);
		}
		
		repository.save(order);
		orderItemRepository.saveAll(order.getItems());
		
		return new OrderDTO(order);
	}
}
