package com.greenfox.service;

import com.greenfox.model.ChatMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface MessageRepository extends CrudRepository<ChatMessage, Long> {
}
