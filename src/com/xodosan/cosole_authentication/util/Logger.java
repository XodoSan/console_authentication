package com.xodosan.cosole_authentication.util;

import com.xodosan.cosole_authentication.constant.Error;

import java.util.HashMap;
import java.util.Map;

public class Logger {
  private static final Map errors = new HashMap<>();

  public Logger() {
    errors.put(Error.UNEXPECTED_COMMAND, "Invalid command");
    errors.put(Error.BANNED_USER, "You are banned!");
    errors.put(Error.PASSWORD_TOO_LONG, "Enter more short password");
    errors.put(Error.USER_EXIST, "This user already exist");
    errors.put(Error.USER_NOT_EXIST, "This user not exist");
    errors.put(Error.WRONG_PASSWORD, "Wrong password");
    errors.put(Error.PASSWORDS_NOT_EQUAL, "Passwords not equal");
    errors.put(Error.SELF_BAN, "Admin can't ban himself");
  }

  public static void DisplayMessageByError(Error error) {
    System.out.println(errors.get(error));
  }
}
