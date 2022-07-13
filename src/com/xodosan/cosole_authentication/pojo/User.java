package com.xodosan.cosole_authentication.pojo;

public class User {
  private String nickName;
  private String password;

  public User(String nickName, String password) {
    this.nickName = nickName;
    this.password = password;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
