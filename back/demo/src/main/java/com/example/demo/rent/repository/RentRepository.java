package com.example.demo.rent.repository;

import com.example.demo.user.model.User;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.rent.model.Rent;

import java.util.Optional;

public interface RentRepository extends CrudRepository<Rent, Long>  {
  Optional<Rent> findById(Long id);
}
