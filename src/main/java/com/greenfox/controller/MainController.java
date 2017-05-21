package com.greenfox.controller;

import com.greenfox.model.*;
import com.greenfox.service.ErrorMessage;
import com.greenfox.repository.MessageRepository;
import com.greenfox.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@Controller
public class MainController {
  private String errorTextOnWebPage;
  private static String logLevel = System.getenv("CHAT_APP_LOGLEVEL");
  private static final String URI = System.getenv("CHAT_APP_PEER_ADDRESS") + "/api/message/receive";
  private static final String CLIENT_ID = System.getenv("CHAT_APP_UNIQUE_ID");
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private MessageRepository messageRepository;

  @RequestMapping("/")
  public String main(Model model) {
    Log log = new Log("/", "REQUEST", "");
    if (!logLevel.equals("ERROR")) {
      log.setLogLevel(logLevel);
      System.out.println(log.toString());
    }
    if (userRepository.count() == 0) {
      return "redirect:/enter";
    } else {
      model.addAttribute("errorTextOnWebPage", errorTextOnWebPage);
      model.addAttribute("userName", userRepository.findOne((long) 1).getName());
      model.addAttribute("messageList", messageRepository.findAll());
      return "index";
    }
  }

  @RequestMapping("/update")
  public String update(@RequestParam("newName") String newName) {
    Log log = new Log("/update", "REQUEST", "newname=" + newName);
    if (!logLevel.equals("ERROR")) {
      log.setLogLevel(logLevel);
      System.out.println(log.toString());
    }
    if (newName.isEmpty()) {
      errorTextOnWebPage = "The username field is empty.";
      return "redirect:/";
    } else {
      User user = userRepository.findOne((long) 1);
      updateExecute(user, newName);
      errorTextOnWebPage = "";
      return "redirect:/";
    }
  }

  @PutMapping("/update/execute")
  public void updateExecute(User user, String newName) {
    Log log = new Log("/update/execute", "PUT", "");
    if (!logLevel.equals("ERROR")) {
      log.setLogLevel(logLevel);
      System.out.println(log.toString());
    }
    user.setName(newName);
    userRepository.save(user);
  }

  @PostMapping("/send")
  public String send(@RequestParam("message") String message) {
    ChatMessage chatMessage = new ChatMessage(userRepository.findOne((long) 1).getName(), message);
    messageRepository.save(chatMessage);
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.postForObject(URI, new ReceivedMessage(chatMessage, new Client(CLIENT_ID)), OkResponse.class);
    return "redirect:/";
  }

  @ExceptionHandler(Exception.class)
  public ErrorMessage showError(Exception e) {
    System.err.println(e.getMessage());
    return new ErrorMessage("Error.");
  }
}
