package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagRequestDTO {
  @NotBlank(message = "name is required")
  @Size(min = 3, max = 100, message = "name must be between 3 and 100 " + "characters")
  private String name;
}
