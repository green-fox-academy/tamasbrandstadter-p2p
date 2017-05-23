package com.greenfox.service;

import com.greenfox.model.ReceivedMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageValidator {
  private List<String> missingList;

  public MessageValidator() {
    this.missingList = new ArrayList<>();
  }

  public List<String> validateMessage(ReceivedMessage receivedMessage) {
    if (receivedMessage.getChatMessage().getId() == 0L) {
      missingList.add("message.id");
    }
    if (receivedMessage.getChatMessage().getMessage() == null || receivedMessage.getChatMessage().getMessage().isEmpty()) {
      missingList.add("message.text");
    }
    if (receivedMessage.getChatMessage().getUserName() == null || receivedMessage.getChatMessage().getUserName().isEmpty()) {
      missingList.add("message.username");
    }
    if (receivedMessage.getChatMessage().getTimestamp() == null) {
      missingList.add("message.timestamp");
    }
    if (receivedMessage.getClient().getId().isEmpty()) {
      missingList.add("client.id");
    }
    return missingList;
  }

  public void clearList() {
    missingList.clear();
  }
}

