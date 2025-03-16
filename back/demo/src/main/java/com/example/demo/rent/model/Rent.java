package com.example.demo.rent.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "rentals")
public class Rent {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

}
