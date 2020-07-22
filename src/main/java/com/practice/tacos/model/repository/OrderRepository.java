package com.practice.tacos.model.repository;

import com.practice.tacos.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long>
{
}
