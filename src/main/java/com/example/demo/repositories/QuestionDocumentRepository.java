package com.example.demo.repositories;

import com.example.demo.models.QuestionElasticDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface QuestionDocumentRepository
    extends ElasticsearchRepository<QuestionElasticDocument, String> {
  List<QuestionElasticDocument> findByTitleContainingOrContentContaining(String title,String content);
}
