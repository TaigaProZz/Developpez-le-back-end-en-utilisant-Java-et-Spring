package com.example.demo.user.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.rent.model.Rent;
import com.example.demo.user.model.User;

public interface UserRepository extends CrudRepository<Rent, Long>  {
  boolean existsByEmail(String email);
  Optional<User> findByEmail(String email);
}
