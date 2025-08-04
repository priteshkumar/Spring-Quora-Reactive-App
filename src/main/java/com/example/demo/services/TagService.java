package com.example.demo.services;

import com.example.demo.adapter.TagAdapter;
import com.example.demo.dto.QuestionResponseDTO;
import com.example.demo.dto.TagRequestDTO;
import com.example.demo.dto.TagResponseDTO;
import com.example.demo.models.Tag;
import com.example.demo.repositories.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TagService implements ITagService {
  private final TagRepository tagRepository;

  @Override
  public Mono<TagResponseDTO> createTag(TagRequestDTO tagRequestDTO) {
    Tag tag = Tag.builder().name(tagRequestDTO.getName()).createdAt(LocalDateTime.now()).build();
    return tagRepository
        .save(tag)
        .map(TagAdapter::toTagResponseDTO)
        .doOnSuccess(response -> System.out.println(response))
        .doOnError(error -> System.out.println(error));
  }

  @Override
  public Mono<TagResponseDTO> getTagById(String id) {
    return tagRepository
        .findById(id)
        .map(TagAdapter::toTagResponseDTO)
        .doOnSuccess(response -> System.out.println(response))
        .doOnError(error -> System.out.println(error));
  }

  @Override
  public Flux<QuestionResponseDTO> getAllQuestions(String tag, int page, int size) {
    return null;
  }
}
