package com.greenfox.model;

public class OkResponse {
  private String status;

  public OkResponse() {
    this.status = "ok";
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
