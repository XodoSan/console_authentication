package com.xodosan.cosole_authentication.page;

import com.xodosan.cosole_authentication.constant.Error;
import com.xodosan.cosole_authentication.pojo.User;
import com.xodosan.cosole_authentication.service.AuthService;
import com.xodosan.cosole_authentication.service.UserService;
import com.xodosan.cosole_authentication.util.Logger;
import com.xodosan.cosole_authentication.util.Tools;

import java.io.Console;
import java.util.Scanner;

public class AccountPage {
  private AuthService authService = new AuthService();
  private UserService userService = new UserService();
  private Scanner in = new Scanner(System.in);
  private Console console = System.console();
  private boolean status;

  public void showLoginMenu(String nickname) {
    status = true;

    while (status) {
      System.out.println("You in account: " + nickname);
      System.out.println("Change password - change");
      System.out.println("Sign out - out");
      System.out.println("Exit - exit");
      System.out.print("Please enter a command: ");
      String command = in.nextLine();

      relocated(command, nickname);
    }
  }

  private void relocated(String command, String nickname) {
    User thisUser = userService.findUserByNickname(nickname);

    switch (command) {
      case ("change") -> {
        String oldPassword = String.valueOf(console.readPassword("Enter your old password: "));
        if (!thisUser.getPassword().equals(Tools.stringHashing(oldPassword))) {
          Logger.DisplayMessageByError(Error.PASSWORDS_NOT_EQUAL);
          return;
        }

        String newPassword = String.valueOf(console.readPassword("Enter your new password: "));
        Error validateResult = Tools.validatePassword(newPassword);
        if (validateResult != Error.NONE) {
          Logger.DisplayMessageByError(validateResult);
          return;
        }

        authService.updatePassword(new User(thisUser.getnickname(), newPassword));
      }
      case ("out") -> status = false;
      case ("exit") -> System.exit(1);
      default -> Logger.DisplayMessageByError(Error.UNEXPECTED_COMMAND);
    }
  }
}
