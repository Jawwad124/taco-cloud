package com.practice.tacos.model.repository;

import com.practice.tacos.model.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, String>
{
}
