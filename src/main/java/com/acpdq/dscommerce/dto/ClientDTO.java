package com.acpdq.dscommerce.dto;

import com.acpdq.dscommerce.entities.User;

public class ClientDTO {
	
	private Long id;
	private String name;
	
	public ClientDTO(User entity) {
		id = entity.getId();
		name = entity.getName();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
