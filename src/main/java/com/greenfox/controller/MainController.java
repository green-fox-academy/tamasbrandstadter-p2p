package com.greenfox.controller;

import com.greenfox.model.ChatMessage;
import com.greenfox.model.Log;
import com.greenfox.model.User;
import com.greenfox.service.ErrorMessage;
import com.greenfox.service.MessageRepository;
import com.greenfox.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {
  private String error;
  private static String env = System.getenv("CHAT_APP_LOGLEVEL");
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private MessageRepository messageRepository;

  @RequestMapping("/")
  public String main(Model model) {
    Log log = new Log("/", "REQUEST", "");
    if (!env.equals("ERROR")) {
      log.setLogLevel(env);
      System.out.println(log.toString());
    }
    if (userRepository.count() == 0) {
      return "redirect:/enter";
    } else {
      model.addAttribute("error", error);
      model.addAttribute("userName", userRepository.findOne((long) 1).getName());
      model.addAttribute("messageList", messageRepository.findAll());
      return "index";
    }
  }

  @RequestMapping("/update")
  public String update(@RequestParam("newName") String newName) {
    Log log = new Log("/update", "REQUEST", "newname=" + newName);
    if (!env.equals("ERROR")) {
      log.setLogLevel(env);
      System.out.println(log.toString());
    }
    if (newName.isEmpty()) {
      error = "The username field is empty.";
      return "redirect:/";
    } else {
      User user = userRepository.findOne((long) 1);
      updateExecute(user, newName);
      error = "";
      return "redirect:/";
    }
  }

  @PutMapping("/update/execute")
  public void updateExecute(User user, String newName) {
    Log log = new Log("/update/execute", "PUT", "");
    if (!env.equals("ERROR")) {
      log.setLogLevel(env);
      System.out.println(log.toString());
    }
    user.setName(newName);
    userRepository.save(user);
  }

  @RequestMapping("/send")
  public String send(@RequestParam("message") String message) {
    messageRepository.save(new ChatMessage(userRepository.findOne((long)1).getName(), message));
    return "redirect:/";
  }

  @ExceptionHandler(Exception.class)
  public ErrorMessage showError(Exception e) {
    System.err.println(e.getMessage());
    return new ErrorMessage("Error.");
  }
}
