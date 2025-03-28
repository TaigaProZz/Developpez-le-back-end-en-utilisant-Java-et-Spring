package com.example.demo.rent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetRentalUpdateDto {
  private Iterable<GetRentalDto> rentals;
}
