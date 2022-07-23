package com.xodosan.cosole_authentication.util;

import com.xodosan.cosole_authentication.constant.Constants;
import com.xodosan.cosole_authentication.constant.Error;
import com.xodosan.cosole_authentication.pojo.Result;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Tools {
  public static Result validatePassword(String password) {
    if (password.length() > Constants.MAX_CHAR_PASSWORD) {
      return new Result(false, Error.PASSWORD_TOO_LONG);
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
