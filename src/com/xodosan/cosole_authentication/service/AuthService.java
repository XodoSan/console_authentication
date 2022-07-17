package com.xodosan.cosole_authentication.service;

import com.xodosan.cosole_authentication.Error;
import com.xodosan.cosole_authentication.Tools;
import com.xodosan.cosole_authentication.pojo.Result;
import com.xodosan.cosole_authentication.pojo.User;

import java.io.IOException;

public class AuthService {
  private FileService fileService;
  private Tools tools;

  public AuthService(FileService fileService, Tools tools) {
    this.fileService = fileService;
    this.tools = tools;
  }

  public Result registration(User user) throws IOException {
    if (isExist(user.getNickName())) return new Result(false, Error.USER_EXIST);

    user.setPassword(tools.stringHashing(user.getPassword()));
    fileService.writeToFile(user);
    System.out.println("Successfully registration!");

    return new Result(true, Error.NONE);
  }

  public Result login(User user) throws IOException {
    if (!isExist(user.getNickName())) return new Result(false, Error.USER_NOT_EXIST);

    User thisUser = fileService.searchUserByNickName(user.getNickName());
    if (!thisUser.getPassword().equals(tools.stringHashing(user.getPassword())))
      return new Result(false, Error.WRONG_PASSWORD);
    System.out.println("Successfully login!");

    return new Result(true, Error.NONE);
  }

  public void changePassword(User user) throws IOException {
    user.setPassword(tools.stringHashing(user.getPassword()));
    fileService.replacePassword(user);
  }

  private boolean isExist(String nickName) throws IOException {
    if (fileService.searchUserByNickName(nickName) != null) return true;

    return false;
  }
}