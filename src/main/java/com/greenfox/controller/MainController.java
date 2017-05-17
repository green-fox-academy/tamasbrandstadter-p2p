package com.greenfox.controller;

import com.greenfox.service.ErrorMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

  @RequestMapping("/")
  public String main() {
    return "index";
  }

//  @RequestMapping(value = "/")
//  public String log(Model model){
//    System.out.println(System.getenv("CHAT_APP_LOGLEVEL"));
//    return "index";
//  }

  @ExceptionHandler(Exception.class)
  public ErrorMessage showError(Exception e) {
    System.err.println(e.getMessage());
    return new ErrorMessage("Error.");
  }
}
