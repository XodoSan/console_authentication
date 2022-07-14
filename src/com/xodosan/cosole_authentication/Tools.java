package com.xodosan.cosole_authentication;

import com.xodosan.cosole_authentication.pojo.Result;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Tools {
  private final int MAX_CHAR_PASSWORD = 20;

  public Result validatePassword(String password) {
    if (password.length() > MAX_CHAR_PASSWORD) {
      return new Result(false, Error.PASSWORD_TOO_LONG);
    }

    return new Result(true, Error.NONE);
  }

  public Result isPasswordsEqual(String password, String repeatedPassword) {
    if (!password.equals(repeatedPassword)) {
      return new Result(false, Error.PASSWORDS_NOT_EQUAL);
    }

    return new Result(true, Error.NONE);
  }

  public static String stringHashing(String data) {
    StringBuilder hexString = new StringBuilder();

    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");

      byte[] bytes = digest.digest(data.getBytes(StandardCharsets.UTF_8));

      for (int i = 0; i < bytes.length; i++) {
        String hex = Integer.toHexString(0xFF & bytes[i]);
        if (hex.length() == 1)
          hexString.append('0');
        hexString.append(hex);
      }
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

    return hexString.toString();
  }
}
