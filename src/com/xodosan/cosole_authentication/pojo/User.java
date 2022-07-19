package com.xodosan.cosole_authentication.pojo;

public class User {
  private String nickName;
  private String password;
  private boolean isBanned;

  public User(String nickName, String password, boolean isBanned) {
    this.nickName = nickName;
    this.password = password;
    this.isBanned = isBanned;
  }

  public String getNickName() {
    return nickName;
  }

  public String getPassword() {
    return password;
  }

  public boolean isBanned() {
    return isBanned;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setBanned(boolean banned) {
    isBanned = banned;
  }
}
