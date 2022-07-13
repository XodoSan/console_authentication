package com.xodosan.cosole_authentication;

import com.xodosan.cosole_authentication.pojo.Result;

public class Tools {
  private final int MAX_CHAR_PASSWORD = 20;

  public Result validatePassword(String password) {
    if (password.length() > MAX_CHAR_PASSWORD) {
      return new Result(false, Error.password_too_long);
    }

    int specialCharacterCounter = 0;
    for (int i = 0; i < password.length(); i++) {
      char passwordSymbol = password.charAt(i);
      if (passwordSymbol == '$' ||
        passwordSymbol == '#' ||
        passwordSymbol == '?' ||
        passwordSymbol == '!' ||
        passwordSymbol == '_' ||
        passwordSymbol == '=' ||
        passwordSymbol == '%' ||
        passwordSymbol == ';') {
        specialCharacterCounter++;
      }
    }

    if (specialCharacterCounter != 0) {
      return new Result(false, Error.password_contains_special_characters); // make enum to errors
    }

    return new Result(true, Error.none);
  }
}
