package com.example.demo.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tags")
public class Tag {
  @Id private String id;

  @NotBlank(message = "name is required")
  @Size(min = 3, max = 100, message = "name must be between 3 and 100 " + "characters")
  private String name;

  @CreatedDate private LocalDateTime createdAt;

  @Builder.Default
  private List<String> questions = new ArrayList<>();
}
