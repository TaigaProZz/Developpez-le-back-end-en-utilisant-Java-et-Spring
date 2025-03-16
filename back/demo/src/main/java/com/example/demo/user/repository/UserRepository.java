package com.example.demo.user.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.user.model.User;

public interface UserRepository extends CrudRepository<User, Long>  {
  Optional<User> findByEmail(String email);
}
