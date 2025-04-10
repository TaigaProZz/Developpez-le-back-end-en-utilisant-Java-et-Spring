package com.example.demo.rent.controller;

import com.example.demo.rent.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.rent.service.RentService;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/rentals")
public class RentController {

  private final RentService rentService;

  public RentController(RentService rentService) {
    this.rentService = rentService;
  }

  // POST Method to create a rent
  @PostMapping(consumes = "multipart/form-data")
  public ResponseEntity<?> createRental(
          @ModelAttribute CreateRentalDto requestBody,
          @RequestParam("picture") MultipartFile file,
          Principal principal)
  {
    try { // save rent
      rentService.saveRent(requestBody, file, principal);

      return ResponseEntity.ok().body(new CreateRentalUpdateDto("Rental created !"));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("Erreur lors de l'enregistrement du fichier");
    }
  }

  // PUT Methods to update a rent
  @PutMapping(path = "/{id}",  consumes = "multipart/form-data")
  public ResponseEntity<UpdateRentalUpdateDto> updateRental(
          @ModelAttribute UpdateRentalDto requestBody,
          @PathVariable Long id)
  {
    // try to update rent
    rentService.updateRent(id, requestBody);
    return ResponseEntity.ok(new UpdateRentalUpdateDto("Rental updated !"));
  }

  // GET all rents
  @GetMapping()
  public ResponseEntity<GetRentalUpdateDto> getAllRents() {
    // get list of all rents
    Iterable<GetRentalDto> getRentalsDto = rentService.getAllRents();
    return ResponseEntity.ok(new GetRentalUpdateDto(getRentalsDto));
  }

  // GET rent by id
  @GetMapping("/{id}")
  public ResponseEntity<GetRentalDto> getRentalById(@PathVariable Long id) {
    // try to fetch rental by id
    GetRentalDto getRentalDto = rentService.getRentalDtoById(id);
    return ResponseEntity.ok(getRentalDto);
  }
}
