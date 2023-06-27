package com.acpdq.dscommerce.dto;

import java.time.Instant;

import com.acpdq.dscommerce.entities.Payment;

public class PaymentDTO {
	
	private Long id;
	private Instant moment;
	
	public PaymentDTO(Payment entity) {
		id = entity.getId();
		moment = entity.getMoment();
	}

	public Long getId() {
		return id;
	}

	public Instant getMoment() {
		return moment;
	}
}