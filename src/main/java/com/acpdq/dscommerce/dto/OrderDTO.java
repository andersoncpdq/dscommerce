package com.acpdq.dscommerce.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.acpdq.dscommerce.entities.Order;
import com.acpdq.dscommerce.entities.OrderItem;
import com.acpdq.dscommerce.entities.OrderStatus;

public class OrderDTO {
	
	private Long id;
	private Instant moment;
	private OrderStatus status;
	
	private ClientDTO client;
	private PaymentDTO payment;
	private List<OrderItemDTO> items = new ArrayList<>();
	
	public OrderDTO(Order entity) {
		id = entity.getId();
		moment = entity.getMoment();
		status = entity.getStatus();
		client = new ClientDTO(entity.getClient());
		payment = ( entity.getPayment() != null ) ? new PaymentDTO(entity.getPayment()) : null;
		
		for (OrderItem item : entity.getItems())
			items.add( new OrderItemDTO(item) );
	}

	public Long getId() {
		return id;
	}

	public Instant getMoment() {
		return moment;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public ClientDTO getClient() {
		return client;
	}

	public PaymentDTO getPayment() {
		return payment;
	}

	public List<OrderItemDTO> getItems() {
		return items;
	}
	
	public Double getTotal() {
		double sum = 0.0;
		for (OrderItemDTO item : items)
			sum += item.getSubTotal();
		
		return sum;
	}
}