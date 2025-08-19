package com.example.demo.services;

import com.example.demo.dto.LikeRequestDTO;
import com.example.demo.dto.LikeResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ILikeService {
  public Mono<LikeResponseDTO> createLike(LikeRequestDTO likeRequestDTO);

  public Mono<LikeResponseDTO> countLikesByTargetIdandTargetType(
      String targetId, String targetType);

  Mono<LikeResponseDTO> toggleLike(String targetId, String targetType, boolean isLike);
}
