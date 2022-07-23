package com.xodosan.cosole_authentication.pojo;

public class User {
  private String nickname;
  private String password;
  private boolean isBanned;

  public User(String nickname, String password) {
    this.nickname = nickname;
    this.password = password;
  }

  public User(String nickname, String password, boolean isBanned) {
    this(nickname, password);
    this.isBanned = isBanned;
  }

  @Override
  public String toString() {
    return nickname + ' ' + password + ' ' + isBanned + '\n';
  }

  public String getnickname() {
    return nickname;
  }

  public String getPassword() {
    return password;
  }

  public boolean isBanned() {
    return isBanned;
  }

  public void setnickname(String nickname) {
    this.nickname = nickname;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setBanned(boolean banned) {
    isBanned = banned;
  }
}
