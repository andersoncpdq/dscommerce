package com.acpdq.dscommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acpdq.dscommerce.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
