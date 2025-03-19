package com.example.demo.rent.controller;

import com.example.demo.rent.dto.GetRentalDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.rent.service.RentService;

@RestController
@RequestMapping("/rentals")
public class RentController {

  @Autowired
  private RentService rentService;

  @GetMapping("/{id}")
  public ResponseEntity<?> getRentalById(@PathVariable Long id) {

    GetRentalDto getRentalDto = rentService.getRentalDtoById(id);

    if (getRentalDto == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Location introuvable");
    }

    return ResponseEntity.ok(getRentalDto);
  }

}
