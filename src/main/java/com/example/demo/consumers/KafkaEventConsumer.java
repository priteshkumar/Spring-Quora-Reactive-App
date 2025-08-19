package com.example.demo.consumers;

import com.example.demo.config.KafkaConfig;
import com.example.demo.events.ViewCountEvent;
import com.example.demo.repositories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaEventConsumer {
  private final QuestionRepository questionRepository;

  @KafkaListener(
      topics = KafkaConfig.TOPIC_NAME,
      groupId = "view-count" + "-consumer",
      containerFactory = "kafkaListenerContainerFactory")
  public void handleViewCountEvent(ViewCountEvent viewCountEvent) {
    questionRepository
        .findById(viewCountEvent.getTargetId())
        .flatMap(
            question -> {
              Integer views = question.getViewCount();
              question.setViewCount(views == null ? 0 : views + 1);
              return questionRepository.save(question);
            })
        .subscribe(
            updatedQuestion -> {
              System.out.println(
                  "view count " + "incremented for question " + updatedQuestion.getId());
            },
            error -> {
              System.out.println(
                  "error incrementing view count " + "for question " + error.getMessage());
            });
  }
}
