package com.example.demo.rent.controller;

import com.example.demo.rent.dto.CreateRentalDto;
import com.example.demo.rent.dto.GetRentalDto;
import com.example.demo.rent.dto.UpdateRentalDto;
import com.example.demo.rent.model.Rent;
import com.example.demo.user.model.User;
import com.example.demo.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.rent.service.RentService;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@RestController
@RequestMapping("/api/rentals")
public class RentController {

  private final RentService rentService;
  private final UserRepository userRepository;

  public RentController(RentService rentService, UserRepository userRepository) {
    this.rentService = rentService;
    this.userRepository = userRepository;
  }

  @PostMapping(consumes = "multipart/form-data")
  public ResponseEntity<?> createRental(
          @ModelAttribute CreateRentalDto requestBody,
          @RequestParam("picture") MultipartFile file) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = (String) authentication.getPrincipal();
    User user = userRepository.findByEmail(email).orElse(null);

    if (user == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur introuvable");
    }

    rentService.saveRent(requestBody, user.getId());

    HashMap<String, Object> response = new HashMap<>();
    response.put("message", "Rental created !");
    return ResponseEntity.ok(response);
  }

  @PutMapping(path = "/{id}",  consumes = "multipart/form-data")
  public ResponseEntity<?> updateRental(
          @ModelAttribute UpdateRentalDto requestBody,
          @PathVariable Long id) {

    Rent rent = rentService.updateRent(id, requestBody);

    if(rent == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Location introuvable");
    }

    HashMap<String, Object> response = new HashMap<>();
    response.put("message", "Rental updated !");
    return ResponseEntity.ok(response);
  }

  @GetMapping()
  public ResponseEntity<HashMap<String, Object>> getAllRents() {

    Iterable<GetRentalDto> getRentalsDto = rentService.getAllRents();
    HashMap<String, Object> response = new HashMap<>();
    response.put("rentals", getRentalsDto);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getRentalById(@PathVariable Long id) {

    GetRentalDto getRentalDto = rentService.getRentalDtoById(id);

    if (getRentalDto == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Location introuvable");
    }

    return ResponseEntity.ok(getRentalDto);
  }

}
