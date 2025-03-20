package com.example.demo.rent.service;

import com.example.demo.errors.RentNotFoundException;
import com.example.demo.errors.UserNotFoundException;
import com.example.demo.rent.dto.CreateRentalDto;
import com.example.demo.rent.dto.GetRentalDto;
import com.example.demo.rent.dto.UpdateRentalDto;
import com.example.demo.user.model.User;
import com.example.demo.user.service.UserService;
import org.springframework.stereotype.Service;

import com.example.demo.rent.model.Rent;
import com.example.demo.rent.repository.RentRepository;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class RentService {

  private final RentRepository rentRepository;
  private final FileStorageService fileStorageService;
  private final UserService userService;

  public RentService(RentRepository rentRepository, FileStorageService fileStorageService, UserService userService) {
    this.rentRepository = rentRepository;
    this.fileStorageService = fileStorageService;
    this.userService = userService;
  }

  public void saveRent(CreateRentalDto requestBody, MultipartFile file, Principal principal) throws IOException {
    // check if owner user exists, throw error if not found
    User user = userService.findUserByEmail(principal.getName());
    if (user == null) {
      throw new UserNotFoundException("Utilisateur introuvable.");
    }

    // store image file
    String filePath = fileStorageService.saveFile(file);

    // and save object to db
    System.out.println(requestBody);
    Rent rent = new Rent();
    rent.setName(requestBody.getName());
    rent.setSurface(requestBody.getSurface());
    rent.setPrice(requestBody.getPrice());
    rent.setPicture(filePath);
    rent.setDescription(requestBody.getDescription());
    rent.setOwner_id(user.getId());
    rentRepository.save(rent);
  }

  public void updateRent(Long id, UpdateRentalDto rent) {
    // try to fetch the rent, throw error if not found
    Rent rentToUpdate = this.rentRepository.findById(id).orElse(null);
    if (rentToUpdate == null) {
      throw new RentNotFoundException("Location introuvable");
    }

    rentToUpdate.setName(rent.getName());
    rentToUpdate.setSurface(rent.getSurface());
    rentToUpdate.setPrice(rent.getPrice());
    rentToUpdate.setDescription(rent.getDescription());

    this.rentRepository.save(rentToUpdate);
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
    // try to get the rent, throw error if not found
    Rent rent = rentRepository.findById(id).orElse(null);
    if (rent == null) {
      throw new RentNotFoundException("Location introuvable");
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
