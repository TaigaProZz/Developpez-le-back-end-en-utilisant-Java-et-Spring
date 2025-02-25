package com.example.demo.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.rent.model.Rent;
import com.example.demo.rent.repository.RentRepository;

import lombok.Data;

@Data
@Service
public class UserService {
  @Autowired
  private RentRepository rentRepository;


  public Iterable<Rent> getUser() {
    return this.rentRepository.findAll();
  }

}
