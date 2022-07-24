package com.xodosan.cosole_authentication.page;

import com.xodosan.cosole_authentication.constant.Constants;
import com.xodosan.cosole_authentication.constant.Error;
import com.xodosan.cosole_authentication.pojo.User;
import com.xodosan.cosole_authentication.service.AuthService;
import com.xodosan.cosole_authentication.service.UserService;
import com.xodosan.cosole_authentication.util.Logger;
import com.xodosan.cosole_authentication.util.Tools;

import java.io.Console;
import java.util.Scanner;

public class AdminPage {
  private static UserService userService = new UserService();
  private static AuthService authService = new AuthService();
  private Scanner in = new Scanner(System.in);
  private Console console = System.console();
  private boolean status;

  public void showAdminMenu() {
    status = true;

    while (status) {
      System.out.println("You in admin menu");
      System.out.println("Banned user - ban");
      System.out.println("Change your password - change");
      System.out.println("Sign out - out");
      System.out.println("Exit - exit");

      String command = in.nextLine();

      relocated(command);
    }
  }

  private void relocated(String command) {
    User thisUser = userService.findUserByNickname(Constants.ADMIN_NICKNAME);

    switch (command) {
      case ("ban") -> {
        System.out.print("Enter user nickname: ");
        String bannedUserNickname = in.nextLine();

        if (userService.findUserByNickname(bannedUserNickname) == null) {
          Logger.DisplayMessageByError(Error.USER_NOT_EXIST);
          break;
        }

        if (bannedUserNickname.equals(Constants.ADMIN_NICKNAME)) {
          Logger.DisplayMessageByError(Error.SELF_BAN);
          break;
        }

        User bannedUser = userService.findUserByNickname(bannedUserNickname);
        userService.updateBanStatus(bannedUser);
        // System.out.println("Successfully change ban status, user: " + bannedUserNickname);
      }
      case ("change") -> {
        String oldPassword = String.valueOf(console.readPassword("Enter your old password: "));
        if (!Tools.stringHashing(oldPassword).equals(thisUser.getPassword())) {
          Logger.DisplayMessageByError(Error.WRONG_PASSWORD);
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
