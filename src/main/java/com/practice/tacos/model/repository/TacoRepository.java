package com.practice.tacos.model.repository;

import com.practice.tacos.model.Taco;
import org.springframework.data.repository.CrudRepository;

public interface TacoRepository extends CrudRepository<Taco, Long>
{
}
