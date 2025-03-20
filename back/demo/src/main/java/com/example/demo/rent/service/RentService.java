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

  /**
   * Saves a rental property along with its associated image file provided in the request.
   *
   * @param requestBody The data transfer object containing the details of the rental property
   *        such as name, surface area, price, and description.
   * @param file The image file associated with the rental property to be stored.
   * @param principal The security principal containing the email of the currently authenticated user.
   * @throws IOException If an error occurs during file storage.
   */
  public void saveRent(CreateRentalDto requestBody, MultipartFile file, Principal principal) throws IOException {
    // check if owner user exists, throw error if not found
    User user = userService.findUserByEmail(principal.getName());
    if (user == null) {
      throw new UserNotFoundException("Utilisateur introuvable.");
    }

    // store image file
    String filePath = fileStorageService.saveFile(file);

    // and save object to db
    Rent rent = new Rent();
    rent.setName(requestBody.getName());
    rent.setSurface(requestBody.getSurface());
    rent.setPrice(requestBody.getPrice());
    rent.setPicture(filePath);
    rent.setDescription(requestBody.getDescription());
    rent.setOwner_id(user.getId());
    rentRepository.save(rent);
  }

  /**
   * Updates the details of a rental property based on the provided rental information.
   *
   * @param id The unique identifier of the rental property to be updated.
   * @param rent The data transfer object containing the updated rental property details such as
   *             name, surface area, price, and description.
   * @throws RentNotFoundException If the rental property with the specified ID is not found in the database.
   */
  public void updateRent(Long id, UpdateRentalDto rent) {
    // try to fetch the rent, throw error if not found
    Rent rentToUpdate = this.rentRepository.findById(id).orElse(null);
    if (rentToUpdate == null) {
      throw new RentNotFoundException("Location introuvable");
    }

    // map to dto
    rentToUpdate.setName(rent.getName());
    rentToUpdate.setSurface(rent.getSurface());
    rentToUpdate.setPrice(rent.getPrice());
    rentToUpdate.setDescription(rent.getDescription());

    // and save it
    this.rentRepository.save(rentToUpdate);
  }

  /**
   * Retrieves all rental records, maps them to Data Transfer Objects (DTOs),
   * and returns them in an iterable collection.
   *
   * @return an Iterable collection of GetRentalDto objects representing all rental records.
   */
  public Iterable<GetRentalDto> getAllRents() {
    // fetch all rents
    Iterable<Rent> rents = this.rentRepository.findAll();

    // map to dto and add to array every rents
    List<GetRentalDto> allRentsDto = new ArrayList<>();
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

    // return the array
    return allRentsDto;
  }

  /**
   * Retrieves the rental details by its unique identifier and maps it to a GetRentalDto object.
   * Throws a RentNotFoundException if the rental is not found.
   *
   * @param id the unique identifier of the rental
   * @return a GetRentalDto object containing the rental details
   * @throws RentNotFoundException if no rental with the specified id is found
   */
  public GetRentalDto getRentalDtoById(Long id) {
    // try to get the rent, throw error if not found
    Rent rent = rentRepository.findById(id).orElse(null);
    if (rent == null) {
      throw new RentNotFoundException("Location introuvable");
    }

    // map to dto and return it
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
