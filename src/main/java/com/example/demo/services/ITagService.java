package com.example.demo.services;

import com.example.demo.dto.QuestionRequestDTO;
import com.example.demo.dto.QuestionResponseDTO;
import com.example.demo.dto.TagRequestDTO;
import com.example.demo.dto.TagResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITagService {
  public Mono<TagResponseDTO> createTag(TagRequestDTO tagRequestDTO);

  public Mono<TagResponseDTO> getTagById(String id);

  public Flux<QuestionResponseDTO> getAllQuestions(String tag, int page, int size);
}
