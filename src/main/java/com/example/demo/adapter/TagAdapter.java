package com.example.demo.adapter;

import com.example.demo.dto.TagResponseDTO;
import com.example.demo.models.Tag;

public class TagAdapter {
  public static TagResponseDTO toTagResponseDTO(Tag tag) {
    return TagResponseDTO.builder()
        .id(tag.getId())
        .name(tag.getName())
        .createdAt(tag.getCreatedAt())
        .build();
  }
}
