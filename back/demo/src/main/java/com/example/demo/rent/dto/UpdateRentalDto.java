package com.example.demo.rent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentalDto {
  private String name;
  private Long surface;
  private Long price;
  private String picture;
  private String description;
}
