package com.greenfox.model;

import javax.persistence.*;

@Entity
@Table(name= "TB_User")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String name;

  public User(){

  }

  public User(String userName) {
    this.name = userName;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
