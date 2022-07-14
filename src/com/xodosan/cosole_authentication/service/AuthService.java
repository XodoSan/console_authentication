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

  public Result login(User user) {
    return new Result(true, Error.NONE);
  }

  private boolean isExist(String nickName) throws IOException {
    if (fileService.searchUserByNickName(nickName) != null) return true;

    return false;
  }
}