package com.xodosan.cosole_authentication.service;

import com.xodosan.cosole_authentication.constant.Error;
import com.xodosan.cosole_authentication.pojo.Result;
import com.xodosan.cosole_authentication.pojo.User;
import com.xodosan.cosole_authentication.util.Tools;

public class AuthService {
  UserService userService = new UserService();

  public Result registration(User user) {
    if (userService.findUserByNickname(user.getnickname()) != null) {
      return new Result(false, Error.USER_EXIST);
    }

    user.setPassword(Tools.stringHashing(user.getPassword()));
    userService.addUser(user);
    // System.out.println("Successfully registration!"); logger work

    return new Result(true, Error.NONE);
  }

  public Result login(User user) {
    if (userService.findUserByNickname(user.getnickname()) == null) {
      return new Result(false, Error.USER_NOT_EXIST);
    }

    User thisUser = userService.findUserByNickname(user.getnickname());
    if (thisUser.isBanned()) {
      return new Result(false, Error.BANNED_USER);
    }

    if (!thisUser.getPassword().equals(Tools.stringHashing(user.getPassword()))) {
      return new Result(false, Error.WRONG_PASSWORD);
    }
    // System.out.println("Successfully login!"); logger work

    return new Result(true, Error.NONE); // replace pojo result to boolean and logger error
  }

  public void updatePassword(User user) {
    user.setPassword(Tools.stringHashing(user.getPassword()));
    userService.updatePassword(user);
  }
}