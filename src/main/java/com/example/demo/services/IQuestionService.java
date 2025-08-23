package com.example.demo.services;

import com.example.demo.dto.QuestionRequestDTO;
import com.example.demo.dto.QuestionResponseDTO;
import com.example.demo.models.Question;

import com.example.demo.models.QuestionElasticDocument;
import org.springframework.data.domain.PageImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IQuestionService {

  public Mono<QuestionResponseDTO> createQuestion(QuestionRequestDTO questionRequestDTO);

  public Mono<QuestionResponseDTO> getQuestionById(String id);

  public Mono<Void> deleteQuestionById(String id);

  public Flux<QuestionResponseDTO> searchQuestions(String searchTerm, int offset, int page);

  public Flux<QuestionResponseDTO> getAllQuestions(String cursor, int size);

  public Mono<QuestionResponseDTO> addTag(String id, String tag);

  public Mono<PageImpl<QuestionResponseDTO>> getQuestionsByTag(String tag, int offset, int page);

  public List<QuestionElasticDocument> searchQuestionsinElasticsearch(String query);
}
