package com.example.demo.controllers;

import com.example.demo.models.Question;
import com.example.demo.models.Tag;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.QuestionRequestDTO;
import com.example.demo.dto.QuestionResponseDTO;
import com.example.demo.services.IQuestionService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/questions")
public class QuestionController {

  private final IQuestionService questionService;

  @PostMapping()
  public Mono<QuestionResponseDTO> createQuestion(
      @RequestBody QuestionRequestDTO questionRequestDTO) {
    return questionService
        .createQuestion(questionRequestDTO)
        .doOnSuccess(response -> System.out.println("Question created successfully: " + response))
        .doOnError(error -> System.out.println("Error creating question: " + error));
  }

  @GetMapping("/{id}")
  public Mono<QuestionResponseDTO> getQuestionById(@PathVariable String id) {
    return questionService
        .getQuestionById(id)
        .doOnSuccess(response -> System.out.println("question " + response))
        .doOnError(error -> System.out.println("error finding " + "question " + id));
  }

  @GetMapping()
  public Flux<QuestionResponseDTO> getAllQuestions(
      @RequestParam(required = false) String cursor, @RequestParam(defaultValue = "10") int size) {
    return questionService
        .getAllQuestions(cursor, size)
        .doOnError(error -> System.out.println("Error fetching questions: " + error))
        .doOnComplete(() -> System.out.println("Questions fetched successfully"));
  }

  @DeleteMapping("/{id}")
  public Mono<Void> deleteQuestionById(@PathVariable String id) {
    return questionService
        .deleteQuestionById(id)
        .doOnSuccess((val) -> System.out.println("question deleted " + id))
        .doOnError(error -> System.out.println("cant delete question " + error));
  }

  @GetMapping("/search")
  public Flux<QuestionResponseDTO> searchQuestions(
      @RequestParam String query,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    return questionService.searchQuestions(query, page, size);
  }

  @GetMapping("/tag/{tag}")
  public Flux<QuestionResponseDTO> getQuestionsByTag(
      @PathVariable String tag,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    return questionService.getQuestionsByTag(tag,page,size);
  }

  @PutMapping("/{id}/tag/{tag}")
  public Mono<QuestionResponseDTO> addTag(@PathVariable String id,
                                @PathVariable String tag) {
    return questionService
        .addTag(id, tag)
        .doOnSuccess(response -> System.out.println(response))
        .doOnError(error -> System.out.println(error));
  }
}
