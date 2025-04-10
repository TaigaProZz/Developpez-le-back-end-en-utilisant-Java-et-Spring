package com.example.demo.user.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.user.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long>  {
  Optional<User> findByEmail(String email);
}
