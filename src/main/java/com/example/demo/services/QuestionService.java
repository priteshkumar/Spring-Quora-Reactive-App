package com.example.demo.services;

import java.time.LocalDateTime;

import com.example.demo.events.ViewCountEvent;
import com.example.demo.models.Tag;
import com.example.demo.producers.KafkaEventProducer;
import com.example.demo.repositories.TagRepository;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.adapter.QuestionAdapter;
import com.example.demo.dto.QuestionRequestDTO;
import com.example.demo.dto.QuestionResponseDTO;
import com.example.demo.models.Question;
import com.example.demo.repositories.QuestionRepository;
import com.example.demo.utils.CursorUtils;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class QuestionService implements IQuestionService {

  private final QuestionRepository questionRepository;
  private final TagRepository tagRepository;
  private final KafkaEventProducer kafkaEventProducer;

  @Override
  public Mono<QuestionResponseDTO> createQuestion(QuestionRequestDTO questionRequestDTO) {

    Question question =
        Question.builder()
            .title(questionRequestDTO.getTitle())
            .content(questionRequestDTO.getContent())
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    return questionRepository
        .save(question)
        .map(QuestionAdapter::toQuestionResponseDTO)
        .doOnSuccess(response -> System.out.println("Question created successfully: " + response))
        .doOnError(error -> System.out.println("Error creating question: " + error));
  }

  public Mono<QuestionResponseDTO> getQuestionById(String id) {
    return questionRepository
        .findById(id)
        .map(QuestionAdapter::toQuestionResponseDTO)
        .doOnSuccess(
            response -> {
              System.out.println("question with id: " + id + " " + response);
              ViewCountEvent viewCountEvent =
                  new ViewCountEvent(id, "questions", LocalDateTime.now());
              kafkaEventProducer.publishEvent(viewCountEvent);
            })
        .doOnError(error -> System.out.println("cant find question"));
  }

  public Mono<Void> deleteQuestionById(String id) {
    return questionRepository
        .deleteById(id)
        .doOnSuccess((val) -> System.out.println("deleted " + id))
        .doOnError(error -> System.out.println("error deleting question " + error));
  }

  @Override
  public Flux<QuestionResponseDTO> searchQuestions(String searchTerm, int offset, int page) {
    return questionRepository
        .findByTitleOrContentContainingIgnoreCase(searchTerm, PageRequest.of(offset, page))
        .map(QuestionAdapter::toQuestionResponseDTO)
        .doOnError(error -> System.out.println("Error searching questions: " + error))
        .doOnComplete(() -> System.out.println("Questions searched successfully"));

    /*TextCriteria criteria =
            TextCriteria.forDefaultLanguage().matching(searchTerm);
    return questionRepository
        .findAllBy(criteria, PageRequest.of(offset, page))
        .map(QuestionAdapter::toQuestionResponseDTO)
        .doOnError(error -> System.out.println("error searching " + error))
        .doOnComplete(() -> System.out.println("question searched"));*/
  }

  @Override
  public Flux<QuestionResponseDTO> getAllQuestions(String cursor, int size) {
    Pageable pageable = PageRequest.of(0, size);

    if (!CursorUtils.isValidCursor(cursor)) {
      return questionRepository
          .findTop10ByOrderByCreatedAtAsc()
          .take(size)
          .map(QuestionAdapter::toQuestionResponseDTO)
          .doOnError(error -> System.out.println("Error fetching questions: " + error))
          .doOnComplete(() -> System.out.println("Questions fetched successfully"));
    } else {
      LocalDateTime cursorTimeStamp = CursorUtils.parseCursor(cursor);
      return questionRepository
          .findByCreatedAtGreaterThanOrderByCreatedAtAsc(cursorTimeStamp, pageable)
          .map(QuestionAdapter::toQuestionResponseDTO)
          .doOnError(error -> System.out.println("Error fetching questions: " + error))
          .doOnComplete(() -> System.out.println("Questions fetched successfully"));
    }
  }

  @Override
  public Mono<QuestionResponseDTO> addTag(String questionId, String tagId) {

    Mono<QuestionResponseDTO> questionResponseDTOMono =
        Mono.zip(questionRepository.findById(questionId), tagRepository.findById(tagId))
            .flatMap(
                result -> {
                  Question question = result.getT1();
                  Tag tag = result.getT2();
                  question.getTags().add(tag.getId());
                  tag.getQuestions().add(question.getId());
                  return Mono.zip(questionRepository.save(question), tagRepository.save(tag));
                })
            .map(result -> QuestionAdapter.toQuestionResponseDTO(result.getT1()));

    return questionResponseDTOMono;
  }

  @Override
  public Mono<PageImpl<QuestionResponseDTO>> getQuestionsByTag(String tagId, int offset, int page) {
    return questionRepository
        .findByTag(tagId, PageRequest.of(offset, page))
        .map(QuestionAdapter::toQuestionResponseDTO)
        .collectList()
        .zipWith(questionRepository.countByTag(tagId) /*Mono.just(4)*/)
        .map(
            result -> {
              return new PageImpl<>(result.getT1(), PageRequest.of(offset, page), result.getT2());
            })
        .doOnSuccess((response) -> System.out.println("questions fetched " + "by tag " + tagId))
        .doOnError(error -> System.out.println(error));
  }
}
