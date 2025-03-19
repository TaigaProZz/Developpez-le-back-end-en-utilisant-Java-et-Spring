package com.example.demo.rent.service;

import com.example.demo.rent.dto.GetRentalDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.rent.model.Rent;
import com.example.demo.rent.repository.RentRepository;

import lombok.Data;

@Data
@Service
public class RentService {
  @Autowired
  private RentRepository rentRepository;


  public Iterable<Rent> getAllRents() {
    return this.rentRepository.findAll();
  }

  public GetRentalDto getRentalDtoById(Long id) {
    Rent rent = rentRepository.findById(id).orElse(null);
    if (rent == null) {
      return null;
    }
    GetRentalDto dto = new GetRentalDto();
    dto.setId(rent.getId());
    dto.setSurface(rent.getSurface());
    dto.setPrice(rent.getPrice());
    dto.setPicture(rent.getPicture());
    dto.setDescription(rent.getDescription());
    dto.setOwner_id(rent.getOwner_id());
    dto.setCreated_at(rent.getCreatedAt());
    dto.setUpdated_at(rent.getUpdatedAt());
    return dto;
  }


}
