package com.example.demo.controllers;

import com.example.demo.dto.QuestionRequestDTO;
import com.example.demo.dto.QuestionResponseDTO;
import com.example.demo.dto.TagRequestDTO;
import com.example.demo.dto.TagResponseDTO;
import com.example.demo.services.ITagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tags")
public class TagController {
  private final ITagService tagService;

  @PostMapping()
  public Mono<TagResponseDTO> createQuestion(@RequestBody TagRequestDTO tagRequestDTO) {
    return tagService
        .createTag(tagRequestDTO)
        .doOnSuccess(response -> System.out.println("Question created successfully: " + response))
        .doOnError(error -> System.out.println("Error creating tag: " + error));
  }

  @GetMapping("/{id}")
  public Mono<TagResponseDTO> getTagById(@PathVariable String id) {
    return tagService
        .getTagById(id)
        .doOnSuccess(response -> System.out.println("tag " + response))
        .doOnError(error -> System.out.println("error finding " + "tag" + " " + id));
  }
}
