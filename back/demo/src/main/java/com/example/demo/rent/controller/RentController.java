package com.example.demo.rent.controller;

import com.example.demo.rent.dto.CreateRentalDto;
import com.example.demo.rent.dto.GetRentalDto;
import com.example.demo.rent.dto.UpdateRentalDto;
import com.example.demo.rent.model.Rent;
import com.example.demo.user.model.User;
import com.example.demo.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.rent.service.RentService;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.HashMap;

@RestController
@RequestMapping("/api/rentals")
public class RentController {

  private final RentService rentService;
  private final UserService userService;

  public RentController(RentService rentService, UserService userService) {
    this.rentService = rentService;
    this.userService = userService;
  }

  @PostMapping(consumes = "multipart/form-data")
  public ResponseEntity<?> createRental(
          @ModelAttribute CreateRentalDto requestBody,
          @RequestParam("picture") MultipartFile file,
          Principal principal
  ) {

    User user = userService.findUserByEmail(principal.getName());
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
