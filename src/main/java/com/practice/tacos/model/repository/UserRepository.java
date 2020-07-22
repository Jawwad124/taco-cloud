package com.practice.tacos.model.repository;

import com.practice.tacos.model.user.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long>
{
  User findByUsername(String username);
}
