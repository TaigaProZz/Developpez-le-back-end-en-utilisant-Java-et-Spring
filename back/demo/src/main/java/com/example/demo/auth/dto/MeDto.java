package com.example.demo.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeDto {
    private String email;
    private String name;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
