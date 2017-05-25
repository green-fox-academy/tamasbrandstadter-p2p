package com.greenfox.service;

import com.greenfox.model.Log;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class Logger {

  public Logger(){

  }

  public static void showLog(HttpServletRequest request, String parameter) {
    new Log(request.getRequestURI(), request.getMethod(), parameter).showLog();
  }

  public static void showLogWithOutParameter(HttpServletRequest request) {
    new Log(request.getRequestURI(), request.getMethod()).showLog();
  }
}
