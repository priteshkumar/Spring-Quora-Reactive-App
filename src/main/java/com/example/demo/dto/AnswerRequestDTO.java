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
public class AnswerRequestDTO {
  @NotBlank(message = "content is required")
  @Size(
      min = 10,
      max = 10000,
      message = "content must be between 10 and " + "10000" + " " + "characters")
  private String content;

  @NotBlank(message = "question id is required")
  private String questionID;
}
