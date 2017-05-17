package com.greenfox.controller;

import com.greenfox.model.ChatMessage;
import com.greenfox.service.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

  @RequestMapping("/")
  public String main() {
    return "index";
  }

  @ExceptionHandler(Exception.class)
  public ErrorMessage showError(Exception e) {
    System.err.println(e.getMessage());
    return new ErrorMessage("Error.");
  }
}
