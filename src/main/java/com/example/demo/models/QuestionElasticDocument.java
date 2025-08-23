package com.example.demo.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Builder
@Document(indexName = "questions")
public class QuestionElasticDocument {
  @Id private String id;
  private String title;
  private String content;
}
