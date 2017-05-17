package com.greenfox.controller;

import com.greenfox.service.ErrorMessage;
import com.greenfox.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
  @Autowired
  private UserRepository userRepository;

  @RequestMapping("/")
  public String main() {
    if (userRepository.count() == 0) {
      return "redirect:/enter";
    } else {
      return "index";
    }
  }

  @ExceptionHandler(Exception.class)
  public ErrorMessage showError(Exception e) {
    System.err.println(e.getMessage());
    return new ErrorMessage("Error.");
  }
}
