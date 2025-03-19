package com.example.demo.rent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetRentalDto {
  private Long id;
  private String name;
  private Long surface;
  private Long price;
  private String description;
  private String picture;
  private Long owner_id;
  private LocalDateTime created_at;
  private LocalDateTime updated_at;
}
