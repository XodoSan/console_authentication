package com.xodosan.cosole_authentication.service;

import com.xodosan.cosole_authentication.constant.Error;
import com.xodosan.cosole_authentication.pojo.User;
import com.xodosan.cosole_authentication.util.Tools;

public class AuthService {
  UserService userService = new UserService();

  public boolean registration(User user) {
    if (userService.findUserByNickname(user.getnickname()) != null) {
      return false;
    }

    user.setPassword(Tools.stringHashing(user.getPassword()));
    userService.addUser(user);

    return true;
  }

  public Error login(User user) {
    if (userService.findUserByNickname(user.getnickname()) == null) {
      return Error.USER_NOT_EXIST;
    }

    User thisUser = userService.findUserByNickname(user.getnickname());
    if (thisUser.isBanned()) {
      return Error.BANNED_USER;
    }

    if (!thisUser.getPassword().equals(Tools.stringHashing(user.getPassword()))) {
      return Error.WRONG_PASSWORD;
    }

    return Error.NONE;
  }

  public void updatePassword(User user) {
    user.setPassword(Tools.stringHashing(user.getPassword()));
    userService.updatePassword(user);
  }
}