package com.example.demo.adapter;

import com.example.demo.dto.QuestionResponseDTO;
import com.example.demo.models.Question;

import java.util.List;

public class QuestionAdapter {

  public static QuestionResponseDTO toQuestionResponseDTO(Question question) {
    // List<String> tags =
    //      question.getTags().stream().map(tag -> tag.getName()).toList();

    return QuestionResponseDTO.builder()
        .id(question.getId())
        .title(question.getTitle())
        .content(question.getContent())
        .createdAt(question.getCreatedAt())
        .tags(question.getTags())
        .build();
  }
}
