package com.greenfox.repository;

import com.greenfox.model.ChatMessage;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<ChatMessage, Long> {
}
