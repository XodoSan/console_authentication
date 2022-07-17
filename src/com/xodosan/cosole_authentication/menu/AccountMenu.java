package com.xodosan.cosole_authentication.menu;

import com.xodosan.cosole_authentication.Error;
import com.xodosan.cosole_authentication.Tools;
import com.xodosan.cosole_authentication.pojo.Result;
import com.xodosan.cosole_authentication.pojo.User;
import com.xodosan.cosole_authentication.service.AuthService;
import com.xodosan.cosole_authentication.service.FileService;

import java.io.IOException;
import java.util.Scanner;

public class AccountMenu {
  public Scanner in = new Scanner(System.in);
  private static AuthService authService;
  private static Tools tools;
  private static FileService fileService;
  private boolean status;

  public AccountMenu(AuthService authService, Tools tools, FileService fileService) {
    this.authService = authService;
    this.tools = tools;
    this.fileService = fileService;
  }

  public void showLoginMenu(String nickName) throws IOException {
    status = true;

    while (status) {
      System.out.println("You in account: " + nickName);
      System.out.println("Please enter a command");
      System.out.println("Change password - change");
      System.out.println("Sign out - out");
      System.out.println("Exit - exit");

      String command = in.nextLine();

      relocated(command, nickName);
    }
  }

  private void relocated(String command, String nickName) throws IOException {
    User thisUser = fileService.searchUserByNickName(nickName);

    switch (command) {
      case ("change"):
        System.out.println("Enter your old password: ");
        String oldPassword = in.nextLine();

        Result compareResult = tools.isPasswordsEqual(tools.stringHashing(oldPassword), thisUser.getPassword());
        if (!compareResult.isResult()) {
          System.out.println(compareResult.getError());
          return;
        }

        System.out.println("Enter new password: ");
        String newPassword = in.nextLine();

        Result validateResult = tools.validatePassword(newPassword);
        if (!validateResult.isResult()) {
          System.out.println(validateResult.getError());
          return;
        }

        authService.changePassword(new User(thisUser.getNickName(), newPassword));
        break;
      case ("out"):
        status = false;
        break;
      case ("exit"):
        System.exit(1);
      default:
        System.out.println(Error.UNEXPECTED_COMMAND);
        break;
    }
  }
}
