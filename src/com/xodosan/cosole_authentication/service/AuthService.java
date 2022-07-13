package com.xodosan.cosole_authentication.service;

import com.xodosan.cosole_authentication.Error;
import com.xodosan.cosole_authentication.pojo.Result;
import com.xodosan.cosole_authentication.pojo.User;

import java.io.IOException;

public class AuthService {
  private FileService fileService;

  public AuthService(FileService fileService) {
    this.fileService = fileService;
  }

  public Result registration(User user) throws IOException {
    if (isExist(user.getNickName())) return new Result(false, Error.user_exist);
    fileService.writeToFile(user);

    System.out.println("Successfully registration!");
    return new Result(true, Error.none);
  }

  public Result login(User user) {
    return new Result(true, Error.none);
  }

  private boolean isExist(String nickName) throws IOException {
    if (fileService.searchUserByNickName(nickName) != null) return true;

    return false;
  }
}