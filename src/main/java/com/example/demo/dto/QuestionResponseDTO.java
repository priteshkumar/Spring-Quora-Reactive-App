package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.models.Tag;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponseDTO {

  private String id;

  private String title;

  private String content;

  private LocalDateTime createdAt;

  private List<String> tags;

}
