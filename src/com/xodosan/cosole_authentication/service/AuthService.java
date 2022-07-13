package com.xodosan.cosole_authentication.service;

import com.xodosan.cosole_authentication.Error;
import com.xodosan.cosole_authentication.pojo.Result;
import com.xodosan.cosole_authentication.pojo.User;

public class AuthService {
  public Result registration(User user) {
    return new Result(true, Error.none);
  }

  public Result login(User user) {
    return new Result(true, Error.none);
  }

  private boolean isExist(String nickName) {
    //checking in db
    return true;
  }

  private boolean validatePassword(User user) {
    return true;
  }
}