package com.example.demo.rent.service;

import com.example.demo.rent.dto.CreateRentalDto;
import com.example.demo.rent.dto.GetRentalDto;
import com.example.demo.rent.dto.UpdateRentalDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.rent.model.Rent;
import com.example.demo.rent.repository.RentRepository;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class RentService {
  @Autowired
  private RentRepository rentRepository;

  public Rent saveRent(CreateRentalDto rent, String filePath, Long userId) {

    Rent newRent = new Rent();
    newRent.setName(rent.getName());
    newRent.setSurface(rent.getSurface());
    newRent.setPrice(rent.getPrice());
    newRent.setPicture(filePath);
    newRent.setDescription(rent.getDescription());
    newRent.setOwner_id(userId);

    return this.rentRepository.save(newRent);
  }

  public Rent updateRent(Long id, UpdateRentalDto rent) {
    Rent rentToUpdate = this.rentRepository.findById(id).orElse(null);

    if (rentToUpdate == null) {
      return null;
    }

    rentToUpdate.setName(rent.getName());
    rentToUpdate.setSurface(rent.getSurface());
    rentToUpdate.setPrice(rent.getPrice());
    rentToUpdate.setDescription(rent.getDescription());

    return this.rentRepository.save(rentToUpdate);
  }

  public Iterable<GetRentalDto> getAllRents() {
    List<GetRentalDto> allRentsDto = new ArrayList<>();

    Iterable<Rent> rents = this.rentRepository.findAll();
    for (Rent rent : rents) {
      GetRentalDto dto = new GetRentalDto();
      dto.setId(rent.getId());
      dto.setName(rent.getName());
      dto.setSurface(rent.getSurface());
      dto.setPrice(rent.getPrice());
      dto.setPicture(rent.getPicture());
      dto.setDescription(rent.getDescription());
      dto.setOwner_id(rent.getOwner_id());
      dto.setCreated_at(rent.getCreatedAt());
      dto.setUpdated_at(rent.getUpdatedAt());

      allRentsDto.add(dto);
    }

    return allRentsDto;
  }

  public GetRentalDto getRentalDtoById(Long id) {
    Rent rent = rentRepository.findById(id).orElse(null);
    if (rent == null) {
      return null;
    }
    GetRentalDto dto = new GetRentalDto();
    dto.setId(rent.getId());
    dto.setName(rent.getName());
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
