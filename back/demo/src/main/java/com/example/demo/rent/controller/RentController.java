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

import java.io.File;
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
          Principal principal
  ) {

    // get user id to link it to the rent
    User user = userService.findUserByEmail(principal.getName());
    if (user == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur introuvable");
    }
    Long userId = user.getId();

    try {
      // define file's save directory
      String uploadsDir = new File("./uploads/images/").getCanonicalPath();
      File directory = new File(uploadsDir);

      if (!directory.exists() && !directory.mkdirs()) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la création du dossier d'upload");
      }

      // check if file is empty
      if (file.isEmpty()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le fichier est vide");
      }

      // check if file is an image
      if (!file.getContentType().startsWith("image/")) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le fichier n'est pas une image");
      }

      // create filename and file, and save it
      String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
      File destinationFile = new File(directory, fileName);
      file.transferTo(destinationFile);

      // define filepath who will be saved in db
      String filePath = "/api/images/" + fileName;

      // save rent in db
      rentService.saveRent(requestBody, filePath, userId);

      // response
      HashMap<String, Object> response = new HashMap<>();
      response.put("message", "Rental created !");
      return ResponseEntity.ok(response);

    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'enregistrement du fichier");
    }
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
