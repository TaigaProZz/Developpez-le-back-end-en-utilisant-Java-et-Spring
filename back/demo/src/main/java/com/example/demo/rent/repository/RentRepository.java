package com.example.demo.rent.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.rent.model.Rent;

public interface RentRepository extends CrudRepository<Rent, Long>  {
  
}
