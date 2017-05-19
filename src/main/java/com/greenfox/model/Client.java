package com.greenfox.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Client {
  @JsonProperty(value = "id")
  private String id;

  public Client(String id){
    this.id = id;
  }

  public Client() {

  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
