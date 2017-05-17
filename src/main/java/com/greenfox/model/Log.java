package com.greenfox.model;

import java.sql.Timestamp;

public class Log {
  private String path;
  private String methodType;
  private Timestamp dateAndTime;
  private String logLevel;
  private String requestData;

  public Log() {
  }

  public Log(String path, String methodType, String requestData) {
    this.path = path;
    this.methodType = methodType;
    this.requestData = requestData;
    this.dateAndTime = new Timestamp(System.currentTimeMillis() / 1000);
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getMethodType() {
    return methodType;
  }

  public void setMethodType(String methodType) {
    this.methodType = methodType;
  }

  public Timestamp getDateAndTime() {
    return dateAndTime;
  }

  public void setDateAndTime(Timestamp dateAndTime) {
    this.dateAndTime = dateAndTime;
  }

  public String getLogLevel() {
    return logLevel;
  }

  public void setLogLevel(String logLevel) {
    this.logLevel = logLevel;
  }

  public String getRequestData() {
    return requestData;
  }

  public void setRequestData(String requestData) {
    this.requestData = requestData;
  }
}
