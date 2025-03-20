package com.example.demo.rent.controller;

import com.example.demo.errors.RentNotFoundException;
import com.example.demo.errors.UserNotFoundException;
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

import java.io.File;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/rentals")
public class RentController {

  private final RentService rentService;
  private final UserService userService;

  public RentController(RentService rentService, UserService userService) {
    this.rentService = rentService;
    this.userService = userService;
  }

  /**
   * Handles the creation of a new rental. This method allows the user to upload
   * a rental along with an associated image file.
   *
   * @param requestBody The details of the rental to be created, encapsulated in a {@link CreateRentalDto}.
   * @param file The image file to be associated with the rental.
   * @param principal The security principal of the user initiating the request, used to identify the owner of the rental.
   * @return A {@link ResponseEntity} containing the status of the rental creation operation and
   *         additional details such as a success message and the file path of the uploaded image.
   */
  @PostMapping(consumes = "multipart/form-data")
  public ResponseEntity<?> createRental(
          @ModelAttribute CreateRentalDto requestBody,
          @RequestParam("picture") MultipartFile file,
          Principal principal)
  {
    try {
      // save rent
      rentService.saveRent(requestBody, file, principal);

      return ResponseEntity.ok().body(Map.of("message", "Rental created !"));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("Erreur lors de l'enregistrement du fichier");
    }
  }

  @PutMapping(path = "/{id}",  consumes = "multipart/form-data")
  public ResponseEntity<?> updateRental(
          @ModelAttribute UpdateRentalDto requestBody,
          @PathVariable Long id)
  {
    // try to update rent
    rentService.updateRent(id, requestBody);

    return ResponseEntity.ok(Map.of("message", "Rental updated !"));
  }

  @GetMapping()
  public ResponseEntity<Map<String, Iterable<GetRentalDto>>> getAllRents() {
    // get list of all rents
    Iterable<GetRentalDto> getRentalsDto = rentService.getAllRents();

    return ResponseEntity.ok(Map.of("rentals", getRentalsDto));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getRentalById(@PathVariable Long id) {
    // try to fetch rental by id
    GetRentalDto getRentalDto = rentService.getRentalDtoById(id);

    return ResponseEntity.ok(getRentalDto);
  }

}
